package com.medipay.util;

public class Constants {
    
    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    public static final String ROLE_PATIENT = "ROLE_PATIENT";
    
    // JWT
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    // API Endpoints
    public static final String API_BASE = "/api";
    public static final String AUTH_BASE = API_BASE + "/auth";
    public static final String PATIENT_BASE = API_BASE + "/patient";
    public static final String DOCTOR_BASE = API_BASE + "/doctor";
    public static final String ADMIN_BASE = API_BASE + "/admin";
    public static final String APPOINTMENT_BASE = API_BASE + "/appointments";
    public static final String PAYMENT_BASE = API_BASE + "/payments";
    
    // Messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String DOCTOR_NOT_FOUND = "Doctor not found";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
    public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
    public static final String PAYMENT_NOT_FOUND = "Payment not found";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String REGISTRATION_SUCCESS = "Registration successful";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    
    private Constants() {
        // Private constructor to prevent instantiation
    }
}
