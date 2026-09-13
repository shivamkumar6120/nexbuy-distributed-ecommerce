package com.nexbuy.payment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

	@NotNull
	private Long orderId;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
}