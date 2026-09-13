package com.nexbuy.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponse {

    private Long id;
    private String userEmail;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
}