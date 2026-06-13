package com.medipay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
    // User Statistics
    private Long totalUsers;
    private Long totalPatients;
    private Long totalDoctors;
    private Long activeUsers;
    
    // Doctor Statistics
    private Long approvedDoctors;
    private Long pendingDoctors;
    
    // Appointment Statistics
    private Long totalAppointments;
    private Long todayAppointments;
    private Long pendingAppointments;
    private Long completedAppointments;
    
    // Payment Statistics
    private Long totalPayments;
    private Long successfulPayments;
    private Double totalRevenue;
    private Double todayRevenue;
}
