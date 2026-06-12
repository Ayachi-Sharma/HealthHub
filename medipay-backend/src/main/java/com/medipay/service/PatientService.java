package com.medipay.service;

import com.medipay.dto.request.PatientUpdateRequest;
import com.medipay.dto.response.DoctorResponse;
import com.medipay.dto.response.PatientDashboardResponse;
import com.medipay.dto.response.PatientProfileResponse;
import com.medipay.entity.Appointment;
import com.medipay.entity.Appointment.AppointmentStatus;
import com.medipay.entity.Doctor;
import com.medipay.entity.Patient;
import com.medipay.entity.User;
import com.medipay.exception.ResourceNotFoundException;
import com.medipay.exception.UnauthorizedException;
import com.medipay.repository.AppointmentRepository;
import com.medipay.repository.DoctorRepository;
import com.medipay.repository.PatientRepository;
import com.medipay.repository.PaymentRepository;
import com.medipay.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Get current authenticated user email
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(Constants.UNAUTHORIZED_ACCESS);
        }
        return authentication.getName();
    }

    // Get current patient
    private Patient getCurrentPatient() {
        String email = getCurrentUserEmail();
        return patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND));
    }

    // Get patient profile
    public PatientProfileResponse getProfile() {
        Patient patient = getCurrentPatient();
        User user = patient.getUser();

        return PatientProfileResponse.builder()
                .id(patient.getId())
                .userId(user.getId())
                .fullName(patient.getFullName())
                .email(user.getEmail())
                .phone(patient.getPhone())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .build();
    }

    // Update patient profile
    @Transactional
    public PatientProfileResponse updateProfile(PatientUpdateRequest request) {
        Patient patient = getCurrentPatient();

        // Update only non-null fields
        if (request.getFullName() != null) {
            patient.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            patient.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            patient.setAddress(request.getAddress());
        }
        if (request.getDateOfBirth() != null) {
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            patient.setGender(request.getGender());
        }
        if (request.getBloodGroup() != null) {
            patient.setBloodGroup(request.getBloodGroup());
        }

        Patient updatedPatient = patientRepository.save(patient);
        User user = updatedPatient.getUser();

        return PatientProfileResponse.builder()
                .id(updatedPatient.getId())
                .userId(user.getId())
                .fullName(updatedPatient.getFullName())
                .email(user.getEmail())
                .phone(updatedPatient.getPhone())
                .address(updatedPatient.getAddress())
                .dateOfBirth(updatedPatient.getDateOfBirth())
                .gender(updatedPatient.getGender())
                .bloodGroup(updatedPatient.getBloodGroup())
                .build();
    }

    // Get all approved doctors
    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findByIsApprovedTrue();
        return doctors.stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    // Search doctors by specialization
    public List<DoctorResponse> searchDoctorsBySpecialization(String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
        return doctors.stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    // Get doctor by ID
    public DoctorResponse getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.DOCTOR_NOT_FOUND));

        if (!doctor.getIsApproved()) {
            throw new ResourceNotFoundException("Doctor is not approved yet");
        }

        return mapToDoctorResponse(doctor);
    }

    // Get patient dashboard data
    public PatientDashboardResponse getDashboard() {
        Patient patient = getCurrentPatient();
        Long patientId = patient.getId();

        // Get all appointments
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        
        // Count appointments by status
        Long totalAppointments = (long) appointments.size();
        Long upcomingAppointments = appointmentRepository
                .findUpcomingAppointmentsByPatient(patientId, LocalDate.now())
                .stream()
                .filter(a -> a.getStatus() == AppointmentStatus.APPROVED)
                .count();
        Long completedAppointments = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                .count();
        Long cancelledAppointments = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED || a.getStatus() == AppointmentStatus.REJECTED)
                .count();

        // Get total payments
        Long totalPayments = (long) paymentRepository.findByPatientId(patientId).size();

        return PatientDashboardResponse.builder()
                .totalAppointments(totalAppointments)
                .upcomingAppointments(upcomingAppointments)
                .completedAppointments(completedAppointments)
                .cancelledAppointments(cancelledAppointments)
                .totalPayments(totalPayments)
                .build();
    }

    // Helper method to map Doctor entity to DoctorResponse
    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .userId(doctor.getUser().getId())
                .fullName(doctor.getFullName())
                .email(doctor.getUser().getEmail())
                .phone(doctor.getPhone())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .qualification(doctor.getQualification())
                .consultationFee(doctor.getConsultationFee())
                .bio(doctor.getBio())
                .isApproved(doctor.getIsApproved())
                .build();
    }
}
