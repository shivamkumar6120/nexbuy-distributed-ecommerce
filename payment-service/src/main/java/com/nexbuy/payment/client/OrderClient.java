package com.nexbuy.payment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.nexbuy.payment.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

@Component	
@RequiredArgsConstructor
public class OrderClient {

    private final RestClient restClient;

    public OrderResponse getOrderById(Long orderId) {

        return restClient.get()
                .uri("http://localhost:8083/api/orders/{id}",
                        orderId)
                .retrieve()
                .body(OrderResponse.class);
    }
    public void confirmOrder(Long orderId) {

        restClient
                .put()
                .uri("http://localhost:8083/api/orders/{id}/status?status=CONFIRMED",
                        orderId)
                .retrieve()
                .toBodilessEntity();
    }
}