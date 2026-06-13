package com.medipay.dto.response;

import com.medipay.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private Long appointmentId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private Double amount;
    private String currency;
    private PaymentStatus status;
    private String patientName;
    private String doctorName;
    private String specialization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
