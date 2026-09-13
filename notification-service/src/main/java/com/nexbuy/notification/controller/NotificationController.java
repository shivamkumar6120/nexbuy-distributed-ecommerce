package com.nexbuy.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexbuy.notification.dto.NotificationRequest;
import com.nexbuy.notification.dto.NotificationResponse;
import com.nexbuy.notification.service.NotificationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse>
            sendNotification(
                    @Valid
                    @RequestBody
                    NotificationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService
                        .sendNotification(request));
    }

    @GetMapping("/my-notifications")
    public ResponseEntity<List<NotificationResponse>>
            getMyNotifications() {

        return ResponseEntity.ok(
                notificationService
                        .getMyNotifications());
    }
}