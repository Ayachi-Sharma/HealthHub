package com.medipay.dto.request;

import com.medipay.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentStatusRequest {
    
    @NotNull(message = "Status is required")
    private AppointmentStatus status;
    
    private String notes;
}
