package com.medipay.controller;

import com.medipay.dto.request.PaymentVerificationRequest;
import com.medipay.dto.response.ApiResponse;
import com.medipay.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;

    // ==================== Payment Order Creation ====================

    @PostMapping("/order/{appointmentId}")
    public ResponseEntity<ApiResponse> createPaymentOrder(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Payment order created successfully", 
                        paymentService.createPaymentOrder(appointmentId))
        );
    }

    // ==================== Payment Verification ====================

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyPayment(@Valid @RequestBody PaymentVerificationRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Payment verified successfully", 
                        paymentService.verifyPayment(request))
        );
    }

    // ==================== Payment History ====================

    @GetMapping("/history")
    public ResponseEntity<ApiResponse> getMyPayments() {
        return ResponseEntity.ok(
                ApiResponse.success("Payment history retrieved successfully", 
                        paymentService.getMyPayments())
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Payment retrieved successfully", 
                        paymentService.getPaymentById(paymentId))
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse> getPaymentByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Payment retrieved successfully", 
                        paymentService.getPaymentByAppointment(appointmentId))
        );
    }
}
