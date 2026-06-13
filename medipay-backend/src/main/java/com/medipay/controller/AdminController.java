package com.medipay.controller;

import com.medipay.dto.request.DoctorApprovalRequest;
import com.medipay.dto.request.UserStatusUpdateRequest;
import com.medipay.dto.response.ApiResponse;
import com.medipay.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final AdminService adminService;

    // ==================== Dashboard ====================

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboard() {
        return ResponseEntity.ok(
                ApiResponse.success("Dashboard data retrieved successfully", adminService.getDashboard())
        );
    }

    // ==================== User Management ====================

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success("Users retrieved successfully", adminService.getAllUsers())
        );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.success("User retrieved successfully", adminService.getUserById(userId))
        );
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<ApiResponse> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UserStatusUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("User status updated successfully", 
                        adminService.updateUserStatus(userId, request))
        );
    }

    // ==================== Patient Management ====================

    @GetMapping("/patients")
    public ResponseEntity<ApiResponse> getAllPatients() {
        return ResponseEntity.ok(
                ApiResponse.success("Patients retrieved successfully", adminService.getAllPatients())
        );
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<ApiResponse> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(
                ApiResponse.success("Patient retrieved successfully", adminService.getPatientById(patientId))
        );
    }

    // ==================== Doctor Management ====================

    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse> getAllDoctors() {
        return ResponseEntity.ok(
                ApiResponse.success("Doctors retrieved successfully", adminService.getAllDoctors())
        );
    }

    @GetMapping("/doctors/pending")
    public ResponseEntity<ApiResponse> getPendingDoctors() {
        return ResponseEntity.ok(
                ApiResponse.success("Pending doctors retrieved successfully", 
                        adminService.getPendingDoctors())
        );
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<ApiResponse> getDoctorById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(
                ApiResponse.success("Doctor retrieved successfully", adminService.getDoctorById(doctorId))
        );
    }

    @PutMapping("/doctors/{doctorId}/approval")
    public ResponseEntity<ApiResponse> approveDoctorStatus(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorApprovalRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Doctor approval status updated successfully", 
                        adminService.approveDoctorStatus(doctorId, request))
        );
    }

    // ==================== Appointment Management ====================

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse> getAllAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved successfully", adminService.getAllAppointments())
        );
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment retrieved successfully", 
                        adminService.getAppointmentById(appointmentId))
        );
    }

    // ==================== Payment Management ====================

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> getAllPayments() {
        return ResponseEntity.ok(
                ApiResponse.success("Payments retrieved successfully", adminService.getAllPayments())
        );
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Payment retrieved successfully", adminService.getPaymentById(paymentId))
        );
    }

    // ==================== System Statistics ====================

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getSystemStats() {
        return ResponseEntity.ok(
                ApiResponse.success("System statistics retrieved successfully", adminService.getSystemStats())
        );
    }
}
