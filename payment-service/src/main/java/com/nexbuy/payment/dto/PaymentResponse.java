package com.nexbuy.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nexbuy.payment.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long orderId;
    private String userEmail;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}