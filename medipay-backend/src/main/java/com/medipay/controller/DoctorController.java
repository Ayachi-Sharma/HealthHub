package com.medipay.controller;

import com.medipay.dto.request.AppointmentStatusRequest;
import com.medipay.dto.request.DoctorUpdateRequest;
import com.medipay.dto.request.TimeSlotRequest;
import com.medipay.dto.response.ApiResponse;
import com.medipay.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DoctorController {

    private final DoctorService doctorService;

    // ==================== Profile Management ====================

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile() {
        return ResponseEntity.ok(
                ApiResponse.success("Profile retrieved successfully", doctorService.getProfile())
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@Valid @RequestBody DoctorUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Profile updated successfully", doctorService.updateProfile(request))
        );
    }

    // ==================== Time Slot Management ====================

    @PostMapping("/slots")
    public ResponseEntity<ApiResponse> createTimeSlot(@Valid @RequestBody TimeSlotRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Time slot created successfully", doctorService.createTimeSlot(request))
        );
    }

    @GetMapping("/slots")
    public ResponseEntity<ApiResponse> getMyTimeSlots() {
        return ResponseEntity.ok(
                ApiResponse.success("Time slots retrieved successfully", doctorService.getMyTimeSlots())
        );
    }

    @GetMapping("/slots/available")
    public ResponseEntity<ApiResponse> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                ApiResponse.success("Available slots retrieved successfully", 
                        doctorService.getAvailableSlots(date))
        );
    }

    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<ApiResponse> deleteTimeSlot(@PathVariable Long slotId) {
        doctorService.deleteTimeSlot(slotId);
        return ResponseEntity.ok(
                ApiResponse.success("Time slot deleted successfully", null)
        );
    }

    // ==================== Appointment Management ====================

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse> getMyAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success("Appointments retrieved successfully", doctorService.getMyAppointments())
        );
    }

    @GetMapping("/appointments/today")
    public ResponseEntity<ApiResponse> getTodayAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success("Today's appointments retrieved successfully", 
                        doctorService.getTodayAppointments())
        );
    }

    @GetMapping("/appointments/upcoming")
    public ResponseEntity<ApiResponse> getUpcomingAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success("Upcoming appointments retrieved successfully", 
                        doctorService.getUpcomingAppointments())
        );
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment retrieved successfully", 
                        doctorService.getAppointmentById(appointmentId))
        );
    }

    @PutMapping("/appointments/{appointmentId}/status")
    public ResponseEntity<ApiResponse> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentStatusRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Appointment status updated successfully", 
                        doctorService.updateAppointmentStatus(appointmentId, request))
        );
    }

    // ==================== Dashboard ====================

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboard() {
        return ResponseEntity.ok(
                ApiResponse.success("Dashboard data retrieved successfully", doctorService.getDashboard())
        );
    }
}
