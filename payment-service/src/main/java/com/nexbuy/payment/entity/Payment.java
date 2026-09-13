package com.nexbuy.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long orderId;

	private String userEmail;

	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	private LocalDateTime createdAt;
}