package com.nexbuy.order.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemResponse {
	private Long productId;
	private Integer quantity;
	private BigDecimal price;

}
