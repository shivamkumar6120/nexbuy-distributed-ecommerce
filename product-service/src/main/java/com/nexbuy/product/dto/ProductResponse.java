package com.nexbuy.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nexbuy.product.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
}
