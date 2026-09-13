package com.nexbuy.notification.dto;

import java.time.LocalDateTime;

import com.nexbuy.notification.entity.NotificationStatus;
import com.nexbuy.notification.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String userEmail;
    private Long orderId;
    private NotificationType type;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
}