package com.nexbuy.notification.dto;

import com.nexbuy.notification.entity.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

	@NotNull
	private Long orderId;

	@NotNull
	private NotificationType type;

	@NotBlank
	private String message;
}