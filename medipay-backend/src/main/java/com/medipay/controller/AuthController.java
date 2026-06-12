package com.medipay.controller;

import com.medipay.dto.request.DoctorRegisterRequest;
import com.medipay.dto.request.LoginRequest;
import com.medipay.dto.request.PatientRegisterRequest;
import com.medipay.dto.response.ApiResponse;
import com.medipay.dto.response.AuthResponse;
import com.medipay.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/patient")
    public ResponseEntity<ApiResponse> registerPatient(@Valid @RequestBody PatientRegisterRequest request) {
        AuthResponse authResponse = authService.registerPatient(request);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message(authResponse.getMessage())
                .data(authResponse)
                .timestamp(LocalDateTime.now())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<ApiResponse> registerDoctor(@Valid @RequestBody DoctorRegisterRequest request) {
        AuthResponse authResponse = authService.registerDoctor(request);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message(authResponse.getMessage())
                .data(authResponse)
                .timestamp(LocalDateTime.now())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message(authResponse.getMessage())
                .data(authResponse)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        // Extract refresh token from Authorization header
        String refreshToken = authHeader.substring(7); // Remove "Bearer " prefix
        
        AuthResponse authResponse = authService.refreshToken(refreshToken);
        
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message(authResponse.getMessage())
                .data(authResponse)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test() {
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("MediPay API is running!")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }
}
