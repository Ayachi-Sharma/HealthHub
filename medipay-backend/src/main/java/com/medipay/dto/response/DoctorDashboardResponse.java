package com.medipay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDashboardResponse {
    private Long totalAppointments;
    private Long todayAppointments;
    private Long upcomingAppointments;
    private Long completedAppointments;
    private Long pendingAppointments;
    private Double totalEarnings;
}
