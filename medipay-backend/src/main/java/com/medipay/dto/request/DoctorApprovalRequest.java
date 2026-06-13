package com.medipay.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorApprovalRequest {
    
    @NotNull(message = "Approval status is required")
    private Boolean isApproved;
    
    private String remarks;
}
