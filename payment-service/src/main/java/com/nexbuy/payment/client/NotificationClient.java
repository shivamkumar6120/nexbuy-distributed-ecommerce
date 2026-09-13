package com.nexbuy.payment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.nexbuy.payment.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestClient restClient;

    public void sendNotification(
            NotificationRequest request) {

        restClient
                .post()
                .uri("http://localhost:8086/api/notifications")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}