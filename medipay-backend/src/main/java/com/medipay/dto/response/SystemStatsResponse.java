package com.medipay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatsResponse {
    private Map<String, Long> appointmentsByStatus;
    private Map<String, Long> paymentsByStatus;
    private Map<String, Long> usersByRole;
    private Map<String, Double> revenueByMonth;
}
