package com.medipay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Patient/Doctor specific fields
    private Long profileId;
    private String specialization;  // For doctors
    private Boolean isApproved;     // For doctors
}
