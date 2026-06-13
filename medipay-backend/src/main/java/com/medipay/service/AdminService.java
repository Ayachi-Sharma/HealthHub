package com.medipay.service;

import com.medipay.dto.request.DoctorApprovalRequest;
import com.medipay.dto.request.UserStatusUpdateRequest;
import com.medipay.dto.response.*;
import com.medipay.entity.*;
import com.medipay.exception.BadRequestException;
import com.medipay.exception.ResourceNotFoundException;
import com.medipay.exception.UnauthorizedException;
import com.medipay.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;
    private final RoleRepository roleRepository;

    // ==================== Authentication Helper ====================

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }
        return authentication.getName();
    }

    private void verifyAdminRole() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            throw new UnauthorizedException("Admin access required");
        }
    }

    // ==================== Dashboard ====================

    public AdminDashboardResponse getDashboard() {
        verifyAdminRole();
        
        LocalDate today = LocalDate.now();
        
        // User Statistics
        Long totalUsers = userRepository.count();
        Long totalPatients = patientRepository.count();
        Long totalDoctors = doctorRepository.count();
        Long activeUsers = userRepository.countByIsActive(true);
        
        // Doctor Statistics
        Long approvedDoctors = doctorRepository.countByIsApproved(true);
        Long pendingDoctors = doctorRepository.countByIsApproved(false);
        
        // Appointment Statistics
        Long totalAppointments = appointmentRepository.count();
        Long todayAppointments = (long) appointmentRepository.findByAppointmentDate(today).size();
        Long pendingAppointments = appointmentRepository.countByStatus(AppointmentStatus.PENDING);
        Long completedAppointments = appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);
        
        // Payment Statistics
        Long totalPayments = paymentRepository.count();
        Long successfulPayments = paymentRepository.countByStatus(PaymentStatus.SUCCESS);
        Double totalRevenue = paymentRepository.sumAmountByStatus(PaymentStatus.SUCCESS);
        
        // Today's revenue
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        Double todayRevenue = paymentRepository.sumAmountByStatusAndCreatedAtBetween(
                PaymentStatus.SUCCESS, startOfDay, endOfDay);
        
        if (totalRevenue == null) totalRevenue = 0.0;
        if (todayRevenue == null) todayRevenue = 0.0;
        
        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalPatients(totalPatients)
                .totalDoctors(totalDoctors)
                .activeUsers(activeUsers)
                .approvedDoctors(approvedDoctors)
                .pendingDoctors(pendingDoctors)
                .totalAppointments(totalAppointments)
                .todayAppointments(todayAppointments)
                .pendingAppointments(pendingAppointments)
                .completedAppointments(completedAppointments)
                .totalPayments(totalPayments)
                .successfulPayments(successfulPayments)
                .totalRevenue(totalRevenue)
                .todayRevenue(todayRevenue)
                .build();
    }

    // ==================== User Management ====================

    public List<UserResponse> getAllUsers() {
        verifyAdminRole();
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long userId) {
        verifyAdminRole();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserResponse(user);
    }

    @Transactional
    public UserResponse updateUserStatus(Long userId, UserStatusUpdateRequest request) {
        verifyAdminRole();
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Prevent admin from deactivating themselves
        String currentUserEmail = getCurrentUserEmail();
        if (user.getEmail().equals(currentUserEmail) && !request.getIsActive()) {
            throw new BadRequestException("You cannot deactivate your own account");
        }
        
        user.setIsActive(request.getIsActive());
        User updated = userRepository.save(user);
        
        log.info("User {} status updated to {} by admin. Reason: {}", 
                userId, request.getIsActive() ? "active" : "inactive", request.getReason());
        
        return mapToUserResponse(updated);
    }

    // ==================== Patient Management ====================

    public List<PatientProfileResponse> getAllPatients() {
        verifyAdminRole();
        List<Patient> patients = patientRepository.findAllByOrderByCreatedAtDesc();
        return patients.stream()
                .map(this::mapToPatientProfileResponse)
                .collect(Collectors.toList());
    }

    public PatientProfileResponse getPatientById(Long patientId) {
        verifyAdminRole();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return mapToPatientProfileResponse(patient);
    }

    // ==================== Doctor Management ====================

    public List<DoctorResponse> getAllDoctors() {
        verifyAdminRole();
        List<Doctor> doctors = doctorRepository.findAllByOrderByCreatedAtDesc();
        return doctors.stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    public List<DoctorResponse> getPendingDoctors() {
        verifyAdminRole();
        List<Doctor> doctors = doctorRepository.findByIsApproved(false);
        return doctors.stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long doctorId) {
        verifyAdminRole();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return mapToDoctorResponse(doctor);
    }

    @Transactional
    public DoctorResponse approveDoctorStatus(Long doctorId, DoctorApprovalRequest request) {
        verifyAdminRole();
        
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        
        doctor.setIsApproved(request.getIsApproved());
        Doctor updated = doctorRepository.save(doctor);
        
        log.info("Doctor {} approval status updated to {} by admin. Remarks: {}", 
                doctorId, request.getIsApproved() ? "approved" : "rejected", request.getRemarks());
        
        return mapToDoctorResponse(updated);
    }

    // ==================== Appointment Management ====================

    public List<AppointmentResponse> getAllAppointments() {
        verifyAdminRole();
        List<Appointment> appointments = appointmentRepository.findAllByOrderByAppointmentDateDescAppointmentTimeDesc();
        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        verifyAdminRole();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return mapToAppointmentResponse(appointment);
    }

    // ==================== Payment Management ====================

    public List<PaymentResponse> getAllPayments() {
        verifyAdminRole();
        List<Payment> payments = paymentRepository.findAllByOrderByCreatedAtDesc();
        return payments.stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long paymentId) {
        verifyAdminRole();
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return mapToPaymentResponse(payment);
    }

    // ==================== System Statistics ====================

    public SystemStatsResponse getSystemStats() {
        verifyAdminRole();
        
        // Appointments by status
        Map<String, Long> appointmentsByStatus = new HashMap<>();
        for (AppointmentStatus status : AppointmentStatus.values()) {
            Long count = appointmentRepository.countByStatus(status);
            appointmentsByStatus.put(status.name(), count);
        }
        
        // Payments by status
        Map<String, Long> paymentsByStatus = new HashMap<>();
        for (PaymentStatus status : PaymentStatus.values()) {
            Long count = paymentRepository.countByStatus(status);
            paymentsByStatus.put(status.name(), count);
        }
        
        // Users by role
        Map<String, Long> usersByRole = new HashMap<>();
        usersByRole.put("PATIENTS", patientRepository.count());
        usersByRole.put("DOCTORS", doctorRepository.count());
        usersByRole.put("ADMINS", userRepository.countByRolesName("ROLE_ADMIN"));
        
        // Revenue by month (placeholder - can be enhanced with actual monthly data)
        Map<String, Double> revenueByMonth = new HashMap<>();
        Double totalRevenue = paymentRepository.sumAmountByStatus(PaymentStatus.SUCCESS);
        if (totalRevenue == null) totalRevenue = 0.0;
        revenueByMonth.put("TOTAL", totalRevenue);
        
        return SystemStatsResponse.builder()
                .appointmentsByStatus(appointmentsByStatus)
                .paymentsByStatus(paymentsByStatus)
                .usersByRole(usersByRole)
                .revenueByMonth(revenueByMonth)
                .build();
    }

    // ==================== Helper Methods ====================

    private UserResponse mapToUserResponse(User user) {
        String roleName = user.getRoles().isEmpty() ? "NONE" : 
                user.getRoles().iterator().next().getName();
        
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(roleName)
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }

    private PatientProfileResponse mapToPatientProfileResponse(Patient patient) {
        User user = patient.getUser();
        return PatientProfileResponse.builder()
                .patientId(patient.getPatientId())
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .build();
    }

    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        User user = doctor.getUser();
        return DoctorResponse.builder()
                .doctorId(doctor.getDoctorId())
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .qualification(doctor.getQualification())
                .consultationFee(doctor.getConsultationFee())
                .bio(doctor.getBio())
                .isApproved(doctor.getIsApproved())
                .build();
    }

    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        Patient patient = appointment.getPatient();
        User patientUser = patient.getUser();
        Doctor doctor = appointment.getDoctor();
        User doctorUser = doctor.getUser();

        return AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .patientId(patient.getPatientId())
                .patientName(patientUser.getFullName())
                .patientEmail(patientUser.getEmail())
                .patientPhone(patientUser.getPhone())
                .doctorId(doctor.getDoctorId())
                .doctorName(doctorUser.getFullName())
                .specialization(doctor.getSpecialization())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .createdAt(appointment.getCreatedAt())
                .build();
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
