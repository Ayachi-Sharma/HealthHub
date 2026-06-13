package com.medipay.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateRequest {
    
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;
    
    @Size(min = 2, max = 50, message = "Specialization must be between 2 and 50 characters")
    private String specialization;
    
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience cannot exceed 50 years")
    private Integer experience;
    
    @Size(min = 2, max = 100, message = "Qualification must be between 2 and 100 characters")
    private String qualification;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Consultation fee must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Consultation fee cannot exceed 10000")
    private Double consultationFee;
    
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;
}
