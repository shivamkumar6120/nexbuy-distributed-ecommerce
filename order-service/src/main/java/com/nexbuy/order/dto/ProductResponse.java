package com.nexbuy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

	private Long id;
	private String name;
	private BigDecimal price;
	private Integer stockQuantity;

}
