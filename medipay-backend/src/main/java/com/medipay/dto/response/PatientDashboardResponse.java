package com.medipay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDashboardResponse {
    private Long totalAppointments;
    private Long upcomingAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;
    private Long totalPayments;
}
