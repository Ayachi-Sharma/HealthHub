package com.medipay.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusRequest {
    
    @NotNull(message = "Active status is required")
    private Boolean isActive;
    
    private String reason;
}
