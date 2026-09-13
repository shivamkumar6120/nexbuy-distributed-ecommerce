package com.nexbuy.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nexbuy.notification.dto.NotificationRequest;
import com.nexbuy.notification.dto.NotificationResponse;
import com.nexbuy.notification.entity.Notification;
import com.nexbuy.notification.entity.NotificationStatus;
import com.nexbuy.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse sendNotification(
            NotificationRequest request) {

        String userEmail = getCurrentUserEmail();

        Notification notification =
                new Notification();

        notification.setUserEmail(userEmail);
        notification.setOrderId(request.getOrderId());
        notification.setType(request.getType());
        notification.setMessage(request.getMessage());
        notification.setStatus(
                NotificationStatus.SENT);
        notification.setCreatedAt(
                LocalDateTime.now());

        Notification savedNotification =
                notificationRepository.save(notification);

        System.out.println(
                "Sending "
                + request.getType()
                + " to "
                + userEmail
                + ": "
                + request.getMessage());

        return toResponse(savedNotification);
    }

    @Override
    public List<NotificationResponse>
            getMyNotifications() {

        String userEmail =
                getCurrentUserEmail();

        return notificationRepository
                .findByUserEmail(userEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private String getCurrentUserEmail() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    private NotificationResponse toResponse(
            Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getUserEmail(),
                notification.getOrderId(),
                notification.getType(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt());
    }
}