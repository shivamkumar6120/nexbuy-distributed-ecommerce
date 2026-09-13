package com.nexbuy.notification.service;

import java.util.List;

import com.nexbuy.notification.dto.NotificationRequest;
import com.nexbuy.notification.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendNotification(
            NotificationRequest request);

    List<NotificationResponse> getMyNotifications();
}