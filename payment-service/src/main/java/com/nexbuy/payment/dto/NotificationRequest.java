package com.nexbuy.payment.dto;

import com.nexbuy.payment.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationRequest {

    private Long orderId;
    private NotificationType type;
    private String message;
}