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
public class DoctorProfileResponse {
    private Long doctorId;
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
    private Integer experience;
    private String qualification;
    private Double consultationFee;
    private String bio;
    private Boolean isApproved;
    private LocalDateTime createdAt;
}
