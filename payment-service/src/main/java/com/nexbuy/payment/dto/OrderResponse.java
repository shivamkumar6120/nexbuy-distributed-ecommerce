package com.nexbuy.payment.dto;

import java.math.BigDecimal;

import com.nexbuy.payment.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String userEmail;
    private BigDecimal totalAmount;
    private OrderStatus status;
}