package com.medipay.controller;

import com.medipay.dto.request.AppointmentBookingRequest;
import com.medipay.dto.request.PatientUpdateRequest;
import com.medipay.dto.response.ApiResponse;
import com.medipay.dto.response.DoctorResponse;
import com.medipay.dto.response.PatientDashboardResponse;
import com.medipay.dto.response.PatientProfileResponse;
import com.medipay.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@PreAuthorize("hasRole('PATIENT')")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Get patient profile
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile() {
        PatientProfileResponse profile = patientService.getProfile();
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Profile retrieved successfully")
                .data(profile)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    // Update patient profile
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@Valid @RequestBody PatientUpdateRequest request) {
        PatientProfileResponse profile = patientService.updateProfile(request);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Profile updated successfully")
                .data(profile)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    // Get all approved doctors
    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse> getAllDoctors() {
        List<DoctorResponse> doctors = patientService.getAllDoctors();
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Doctors retrieved successfully")
                .data(doctors)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    // Search doctors by specialization
    @GetMapping("/doctors/search")
    public ResponseEntity<ApiResponse> searchDoctors(@RequestParam String specialization) {
        List<DoctorResponse> doctors = patientService.searchDoctorsBySpecialization(specialization);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Search results retrieved successfully")
                .data(doctors)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    // Get doctor by ID
    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<ApiResponse> getDoctorById(@PathVariable Long doctorId) {
        DoctorResponse doctor = patientService.getDoctorById(doctorId);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Doctor details retrieved successfully")
                .data(doctor)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    // Get patient dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboard() {
        PatientDashboardResponse dashboard = patientService.getDashboard();
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Dashboard data retrieved successfully")
                .data(dashboard)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }
}

    // ==================== Appointment Management ====================

    @PostMapping("/appointments/book")
    public ResponseEntity<ApiResponse> bookAppointment(@Valid @RequestBody AppointmentBookingRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment booked successfully", patientService.bookAppointment(request))
        );
    }

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse> getMyAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved successfully", patientService.getMyAppointments())
        );
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment retrieved successfully", 
                        patientService.getAppointmentById(appointmentId))
        );
    }

    @PutMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment cancelled successfully", 
                        patientService.cancelAppointment(appointmentId))
        );
    }
}
