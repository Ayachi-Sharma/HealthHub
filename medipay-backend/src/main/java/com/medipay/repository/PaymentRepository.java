package com.medipay.repository;

import com.medipay.entity.Appointment;
import com.medipay.entity.Payment;
import com.medipay.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAppointmentId(Long appointmentId);
    List<Payment> findByPatientId(Long patientId);
    List<Payment> findByDoctorId(Long doctorId);
    List<Payment> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<Payment> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
    List<Payment> findByStatus(PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.doctor.id = :doctorId AND p.status = 'SUCCESS'")
    BigDecimal getTotalEarningsByDoctor(@Param("doctorId") Long doctorId);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'SUCCESS'")
    BigDecimal getTotalRevenue();
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'SUCCESS'")
    Long countSuccessfulPayments();
    
    // Admin queries
    List<Payment> findAllByOrderByCreatedAtDesc();
    List<Payment> findByPatientOrderByCreatedAtDesc(com.medipay.entity.Patient patient);
    Optional<Payment> findByAppointment(Appointment appointment);
    boolean existsByAppointmentAndStatus(Appointment appointment, PaymentStatus status);
    Long countByStatus(PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status")
    Double sumAmountByStatus(@Param("status") PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.doctor = :doctor AND p.status = :status")
    Double sumAmountByDoctorAndStatus(@Param("doctor") com.medipay.entity.Doctor doctor, @Param("status") PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    Double sumAmountByStatusAndCreatedAtBetween(@Param("status") PaymentStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
