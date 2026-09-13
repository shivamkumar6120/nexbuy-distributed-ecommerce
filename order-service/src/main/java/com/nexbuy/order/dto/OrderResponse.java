package com.nexbuy.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.nexbuy.order.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {
	
	private Long id;
	private String userEmail;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;
	

}
