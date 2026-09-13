package com.nexbuy.order.service;

import com.nexbuy.order.dto.OrderRequest;
import com.nexbuy.order.dto.OrderResponse;

public interface OrderService {
	
	OrderResponse createOrder(OrderRequest request);
	OrderResponse getOrderById(Long id);

}
