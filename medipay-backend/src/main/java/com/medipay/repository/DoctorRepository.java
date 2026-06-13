package com.medipay.repository;

import com.medipay.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    Optional<Doctor> findByUserEmail(String email);
    List<Doctor> findByIsApprovedTrue();
    List<Doctor> findByIsApprovedFalse();
    List<Doctor> findByIsApproved(Boolean isApproved);
    
    @Query("SELECT d FROM Doctor d WHERE d.isApproved = true AND LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))")
    List<Doctor> findBySpecializationContainingIgnoreCase(@Param("specialization") String specialization);
    
    // Admin queries
    List<Doctor> findAllByOrderByCreatedAtDesc();
    Long countByIsApproved(Boolean isApproved);
}
}
