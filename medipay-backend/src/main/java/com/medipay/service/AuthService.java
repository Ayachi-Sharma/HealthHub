package com.medipay.service;

import com.medipay.dto.request.DoctorRegisterRequest;
import com.medipay.dto.request.LoginRequest;
import com.medipay.dto.request.PatientRegisterRequest;
import com.medipay.dto.response.AuthResponse;
import com.medipay.entity.Doctor;
import com.medipay.entity.Patient;
import com.medipay.entity.Role;
import com.medipay.entity.User;
import com.medipay.exception.BadRequestException;
import com.medipay.exception.ResourceNotFoundException;
import com.medipay.repository.DoctorRepository;
import com.medipay.repository.PatientRepository;
import com.medipay.repository.RoleRepository;
import com.medipay.repository.UserRepository;
import com.medipay.util.Constants;
import com.medipay.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Transactional
    public AuthResponse registerPatient(PatientRegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(Constants.EMAIL_ALREADY_EXISTS);
        }

        // Get patient role
        Role patientRole = roleRepository.findByName(Constants.ROLE_PATIENT)
                .orElseThrow(() -> new ResourceNotFoundException("Patient role not found"));

        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(patientRole);
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);

        // Create patient profile
        Patient patient = new Patient();
        patient.setUser(savedUser);
        patient.setFullName(request.getFullName());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        
        patientRepository.save(patient);

        // Generate tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String accessToken = jwtUtil.generateTokenWithRole(savedUser.getEmail(), patientRole.getName());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .email(savedUser.getEmail())
                .role(patientRole.getName())
                .userId(savedUser.getId())
                .message(Constants.REGISTRATION_SUCCESS)
                .build();
    }

    @Transactional
    public AuthResponse registerDoctor(DoctorRegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(Constants.EMAIL_ALREADY_EXISTS);
        }

        // Get doctor role
        Role doctorRole = roleRepository.findByName(Constants.ROLE_DOCTOR)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor role not found"));

        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(doctorRole);
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);

        // Create doctor profile
        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setFullName(request.getFullName());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setExperience(request.getExperience());
        doctor.setQualification(request.getQualification());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setBio(request.getBio());
        doctor.setIsApproved(false); // Doctors need admin approval
        
        doctorRepository.save(doctor);

        // Generate tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String accessToken = jwtUtil.generateTokenWithRole(savedUser.getEmail(), doctorRole.getName());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .email(savedUser.getEmail())
                .role(doctorRole.getName())
                .userId(savedUser.getId())
                .message("Registration successful. Please wait for admin approval.")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        User user = userDetailsService.getUserByEmail(request.getEmail());

        // Generate tokens
        String accessToken = jwtUtil.generateTokenWithRole(user.getEmail(), user.getRole().getName());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .email(user.getEmail())
                .role(user.getRole().getName())
                .userId(user.getId())
                .message(Constants.LOGIN_SUCCESS)
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        // Extract username from refresh token
        String email = jwtUtil.extractUsername(refreshToken);
        
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        User user = userDetailsService.getUserByEmail(email);

        // Validate refresh token
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
            // Generate new access token
            String newAccessToken = jwtUtil.generateTokenWithRole(user.getEmail(), user.getRole().getName());
            
            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .email(user.getEmail())
                    .role(user.getRole().getName())
                    .userId(user.getId())
                    .message("Token refreshed successfully")
                    .build();
        } else {
            throw new BadRequestException("Invalid refresh token");
        }
    }
}
