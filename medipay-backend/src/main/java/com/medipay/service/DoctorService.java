package com.medipay.service;

import com.medipay.dto.request.AppointmentStatusRequest;
import com.medipay.dto.request.DoctorUpdateRequest;
import com.medipay.dto.request.TimeSlotRequest;
import com.medipay.dto.response.*;
import com.medipay.entity.*;
import com.medipay.exception.BadRequestException;
import com.medipay.exception.ResourceNotFoundException;
import com.medipay.exception.UnauthorizedException;
import com.medipay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;

    // ==================== Authentication Helper ====================
    
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }
        return authentication.getName();
    }

    private Doctor getCurrentDoctor() {
        String email = getCurrentUserEmail();
        return doctorRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    // ==================== Profile Management ====================

    public DoctorProfileResponse getProfile() {
        Doctor doctor = getCurrentDoctor();
        User user = doctor.getUser();
        
        return DoctorProfileResponse.builder()
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
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public DoctorProfileResponse updateProfile(DoctorUpdateRequest request) {
        Doctor doctor = getCurrentDoctor();
        User user = doctor.getUser();

        // Update user fields
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        // Update doctor fields
        if (request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }
        if (request.getExperience() != null) {
            doctor.setExperience(request.getExperience());
        }
        if (request.getQualification() != null) {
            doctor.setQualification(request.getQualification());
        }
        if (request.getConsultationFee() != null) {
            doctor.setConsultationFee(request.getConsultationFee());
        }
        if (request.getBio() != null) {
            doctor.setBio(request.getBio());
        }

        userRepository.save(user);
        doctorRepository.save(doctor);

        return getProfile();
    }

    // ==================== Time Slot Management ====================

    @Transactional
    public TimeSlotResponse createTimeSlot(TimeSlotRequest request) {
        Doctor doctor = getCurrentDoctor();

        // Check if doctor is approved
        if (!doctor.getIsApproved()) {
            throw new BadRequestException("Your profile is not approved yet. Please wait for admin approval.");
        }

        // Validate time slot
        if (request.getEndTime().isBefore(request.getStartTime()) || 
            request.getEndTime().equals(request.getStartTime())) {
            throw new BadRequestException("End time must be after start time");
        }

        // Check for overlapping slots
        List<TimeSlot> existingSlots = timeSlotRepository.findByDoctorAndDate(doctor, request.getDate());
        for (TimeSlot slot : existingSlots) {
            if (isTimeOverlapping(request.getStartTime(), request.getEndTime(), 
                                slot.getStartTime(), slot.getEndTime())) {
                throw new BadRequestException("Time slot overlaps with existing slot");
            }
        }

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDoctor(doctor);
        timeSlot.setDate(request.getDate());
        timeSlot.setStartTime(request.getStartTime());
        timeSlot.setEndTime(request.getEndTime());
        timeSlot.setIsBooked(false);

        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return mapToTimeSlotResponse(saved);
    }

    public List<TimeSlotResponse> getMyTimeSlots() {
        Doctor doctor = getCurrentDoctor();
        List<TimeSlot> slots = timeSlotRepository.findByDoctorOrderByDateAscStartTimeAsc(doctor);
        return slots.stream()
                .map(this::mapToTimeSlotResponse)
                .collect(Collectors.toList());
    }

    public List<TimeSlotResponse> getAvailableSlots(LocalDate date) {
        Doctor doctor = getCurrentDoctor();
        List<TimeSlot> slots = timeSlotRepository.findByDoctorAndDateAndIsBooked(doctor, date, false);
        return slots.stream()
                .map(this::mapToTimeSlotResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTimeSlot(Long slotId) {
        Doctor doctor = getCurrentDoctor();
        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Time slot not found"));

        // Check ownership
        if (!slot.getDoctor().getDoctorId().equals(doctor.getDoctorId())) {
            throw new UnauthorizedException("You are not authorized to delete this time slot");
        }

        // Check if booked
        if (slot.getIsBooked()) {
            throw new BadRequestException("Cannot delete a booked time slot");
        }

        timeSlotRepository.delete(slot);
    }

    // ==================== Appointment Management ====================

    public List<AppointmentResponse> getMyAppointments() {
        Doctor doctor = getCurrentDoctor();
        List<Appointment> appointments = appointmentRepository.findByDoctorOrderByAppointmentDateDescAppointmentTimeDesc(doctor);
        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getTodayAppointments() {
        Doctor doctor = getCurrentDoctor();
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, today);
        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getUpcomingAppointments() {
        Doctor doctor = getCurrentDoctor();
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentRepository.findByDoctorAndAppointmentDateAfterAndStatus(
                doctor, today, AppointmentStatus.APPROVED);
        return appointments.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        Doctor doctor = getCurrentDoctor();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Check ownership
        if (!appointment.getDoctor().getDoctorId().equals(doctor.getDoctorId())) {
            throw new UnauthorizedException("You are not authorized to view this appointment");
        }

        return mapToAppointmentResponse(appointment);
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long appointmentId, AppointmentStatusRequest request) {
        Doctor doctor = getCurrentDoctor();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Check ownership
        if (!appointment.getDoctor().getDoctorId().equals(doctor.getDoctorId())) {
            throw new UnauthorizedException("You are not authorized to update this appointment");
        }

        // Validate status transition
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Cannot update status of completed appointment");
        }
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Cannot update status of cancelled appointment");
        }

        appointment.setStatus(request.getStatus());
        if (request.getNotes() != null) {
            appointment.setNotes(request.getNotes());
        }

        // If rejected, free up the time slot
        if (request.getStatus() == AppointmentStatus.REJECTED && appointment.getTimeSlot() != null) {
            TimeSlot slot = appointment.getTimeSlot();
            slot.setIsBooked(false);
            timeSlotRepository.save(slot);
        }

        Appointment updated = appointmentRepository.save(appointment);
        return mapToAppointmentResponse(updated);
    }

    // ==================== Dashboard ====================

    public DoctorDashboardResponse getDashboard() {
        Doctor doctor = getCurrentDoctor();
        LocalDate today = LocalDate.now();

        Long totalAppointments = appointmentRepository.countByDoctor(doctor);
        Long todayAppointments = (long) appointmentRepository.findByDoctorAndAppointmentDate(doctor, today).size();
        Long upcomingAppointments = (long) appointmentRepository.findByDoctorAndAppointmentDateAfterAndStatus(
                doctor, today, AppointmentStatus.APPROVED).size();
        Long completedAppointments = appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.COMPLETED);
        Long pendingAppointments = appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.PENDING);
        
        Double totalEarnings = paymentRepository.sumAmountByDoctorAndStatus(doctor, PaymentStatus.SUCCESS);
        if (totalEarnings == null) {
            totalEarnings = 0.0;
        }

        return DoctorDashboardResponse.builder()
                .totalAppointments(totalAppointments)
                .todayAppointments(todayAppointments)
                .upcomingAppointments(upcomingAppointments)
                .completedAppointments(completedAppointments)
                .pendingAppointments(pendingAppointments)
                .totalEarnings(totalEarnings)
                .build();
    }

    // ==================== Helper Methods ====================

    private boolean isTimeOverlapping(java.time.LocalTime start1, java.time.LocalTime end1,
                                     java.time.LocalTime start2, java.time.LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    private TimeSlotResponse mapToTimeSlotResponse(TimeSlot slot) {
        return TimeSlotResponse.builder()
                .slotId(slot.getSlotId())
                .doctorId(slot.getDoctor().getDoctorId())
                .date(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .isBooked(slot.getIsBooked())
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
}
