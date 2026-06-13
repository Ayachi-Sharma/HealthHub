package com.medipay.service;

import com.medipay.dto.request.PaymentVerificationRequest;
import com.medipay.dto.response.PaymentOrderResponse;
import com.medipay.dto.response.PaymentResponse;
import com.medipay.entity.*;
import com.medipay.exception.BadRequestException;
import com.medipay.exception.ResourceNotFoundException;
import com.medipay.exception.UnauthorizedException;
import com.medipay.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    // ==================== Authentication Helper ====================

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }
        return authentication.getName();
    }

    private Patient getCurrentPatient() {
        String email = getCurrentUserEmail();
        return patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    // ==================== Payment Order Creation ====================

    @Transactional
    public PaymentOrderResponse createPaymentOrder(Long appointmentId) {
        Patient patient = getCurrentPatient();
        
        // Get appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Verify ownership
        if (!appointment.getPatient().getPatientId().equals(patient.getPatientId())) {
            throw new UnauthorizedException("You are not authorized to create payment for this appointment");
        }

        // Check if appointment is approved
        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new BadRequestException("Only approved appointments can be paid");
        }

        // Check if payment already exists
        if (paymentRepository.existsByAppointmentAndStatus(appointment, PaymentStatus.SUCCESS)) {
            throw new BadRequestException("Payment already completed for this appointment");
        }

        Doctor doctor = appointment.getDoctor();
        Double amount = doctor.getConsultationFee();

        try {
            // Create Razorpay order
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (amount * 100)); // Convert to paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "APPT_" + appointmentId);
            
            JSONObject notes = new JSONObject();
            notes.put("appointmentId", appointmentId);
            notes.put("patientId", patient.getPatientId());
            notes.put("doctorId", doctor.getDoctorId());
            orderRequest.put("notes", notes);

            Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            String razorpayOrderId = razorpayOrder.get("id");

            // Create payment record
            Payment payment = new Payment();
            payment.setAppointment(appointment);
            payment.setPatient(patient);
            payment.setDoctor(doctor);
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setAmount(amount);
            payment.setCurrency("INR");
            payment.setStatus(PaymentStatus.PENDING);
            
            paymentRepository.save(payment);

            log.info("Payment order created: {} for appointment: {}", razorpayOrderId, appointmentId);

            return PaymentOrderResponse.builder()
                    .orderId(payment.getPaymentId().toString())
                    .razorpayOrderId(razorpayOrderId)
                    .amount(amount)
                    .currency("INR")
                    .razorpayKeyId(razorpayKeyId)
                    .appointmentId(appointmentId)
                    .doctorName(doctor.getUser().getFullName())
                    .specialization(doctor.getSpecialization())
                    .appointmentDate(appointment.getAppointmentDate().toString())
                    .appointmentTime(appointment.getAppointmentTime().toString())
                    .build();

        } catch (RazorpayException e) {
            log.error("Failed to create Razorpay order: {}", e.getMessage());
            throw new BadRequestException("Failed to create payment order: " + e.getMessage());
        }
    }

    // ==================== Payment Verification ====================

    @Transactional
    public PaymentResponse verifyPayment(PaymentVerificationRequest request) {
        Patient patient = getCurrentPatient();

        // Get appointment
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Verify ownership
        if (!appointment.getPatient().getPatientId().equals(patient.getPatientId())) {
            throw new UnauthorizedException("You are not authorized to verify payment for this appointment");
        }

        // Get payment record
        Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment record not found"));

        // Verify signature
        if (!verifyRazorpaySignature(request.getRazorpayOrderId(), 
                                     request.getRazorpayPaymentId(), 
                                     request.getRazorpaySignature())) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new BadRequestException("Invalid payment signature");
        }

        // Update payment record
        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
        payment.setStatus(PaymentStatus.SUCCESS);
        
        Payment savedPayment = paymentRepository.save(payment);

        log.info("Payment verified successfully: {} for appointment: {}", 
                request.getRazorpayPaymentId(), request.getAppointmentId());

        return mapToPaymentResponse(savedPayment);
    }

    // ==================== Payment History ====================

    public List<PaymentResponse> getMyPayments() {
        Patient patient = getCurrentPatient();
        List<Payment> payments = paymentRepository.findByPatientOrderByCreatedAtDesc(patient);
        return payments.stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long paymentId) {
        Patient patient = getCurrentPatient();
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // Verify ownership
        if (!payment.getPatient().getPatientId().equals(patient.getPatientId())) {
            throw new UnauthorizedException("You are not authorized to view this payment");
        }

        return mapToPaymentResponse(payment);
    }

    public PaymentResponse getPaymentByAppointment(Long appointmentId) {
        Patient patient = getCurrentPatient();
        
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Verify ownership
        if (!appointment.getPatient().getPatientId().equals(patient.getPatientId())) {
            throw new UnauthorizedException("You are not authorized to view this payment");
        }

        Payment payment = paymentRepository.findByAppointment(appointment)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for this appointment"));

        return mapToPaymentResponse(payment);
    }

    // ==================== Helper Methods ====================

    private boolean verifyRazorpaySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(razorpayKeySecret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            
            byte[] hash = mac.doFinal(payload.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            String generatedSignature = hexString.toString();
            return generatedSignature.equals(signature);
            
        } catch (Exception e) {
            log.error("Failed to verify signature: {}", e.getMessage());
            return false;
        }
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        Appointment appointment = payment.getAppointment();
        Patient patient = payment.getPatient();
        Doctor doctor = payment.getDoctor();

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .appointmentId(appointment.getAppointmentId())
                .razorpayOrderId(payment.getRazorpayOrderId())
                .razorpayPaymentId(payment.getRazorpayPaymentId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .patientName(patient.getUser().getFullName())
                .doctorName(doctor.getUser().getFullName())
                .specialization(doctor.getSpecialization())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
