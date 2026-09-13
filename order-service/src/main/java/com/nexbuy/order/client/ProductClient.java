package com.nexbuy.order.client;

import com.nexbuy.order.exception.GlobalExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.nexbuy.order.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component // Spring says: I need to create a ProductClient object.

//Spring creates an object:
//RestClient object
//|
//| baseUrl
//↓
//http://localhost:8082
//And because of @Bean, Spring puts this object inside the Spring IoC Container.

//Spring creates one RestClient bean/object by default from RestClientConfig.
//Spring keeps that bean in its IoC Container.
//When Spring creates ProductClient and sees it needs a RestClient, it injects that same object.
//So ProductClient does not create its own RestClient.

public class ProductClient {

	private final RestClient restClient; // ProductClient needs a RestClient.

	public ProductResponse getProductById(Long productId) {
		return restClient.get().uri("/api/products/{id}", productId).retrieve().body(ProductResponse.class);
	}

	public ProductResponse reduceStock(Long productId, Integer quantity) {
		return restClient.put().uri("/api/products/{id}/stock/reduce?quantity={quantity}", productId, quantity)
				.retrieve().body(ProductResponse.class);
	}

	public ProductResponse increaseStock(Long productId, Integer quantity) {
		return restClient.put().uri("/api/products/{id}/stock/increase?quantity={quantity}", productId, quantity)
				.retrieve().body(ProductResponse.class);
	}
}
