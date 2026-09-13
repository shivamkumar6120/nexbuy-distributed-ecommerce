package com.nexbuy.cart.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.nexbuy.cart.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient restClient;

    public ProductResponse getProductById(Long productId) {

        return restClient.get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .body(ProductResponse.class);
    }
}