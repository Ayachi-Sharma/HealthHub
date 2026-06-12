package com.medipay.repository;

import com.medipay.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDoctorIdAndSlotDate(Long doctorId, LocalDate slotDate);
    List<TimeSlot> findByDoctorIdAndIsAvailableTrue(Long doctorId);
    
    @Query("SELECT t FROM TimeSlot t WHERE t.doctor.id = :doctorId AND t.slotDate >= :startDate AND t.isAvailable = true")
    List<TimeSlot> findAvailableSlotsByDoctorAndDateRange(@Param("doctorId") Long doctorId, @Param("startDate") LocalDate startDate);
    
    Optional<TimeSlot> findByDoctorIdAndSlotDateAndStartTime(Long doctorId, LocalDate slotDate, LocalTime startTime);
    
    @Query("SELECT t FROM TimeSlot t WHERE t.doctor.id = :doctorId AND t.slotDate = :date AND t.startTime = :startTime AND t.endTime = :endTime")
    Optional<TimeSlot> findExistingSlot(@Param("doctorId") Long doctorId, @Param("date") LocalDate date, 
                                       @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
