package com.nexbuy.order.service;

import java.util.List;

import com.nexbuy.order.dto.OrderRequest;
import com.nexbuy.order.dto.OrderResponse;
import com.nexbuy.order.entity.Order;
import com.nexbuy.order.entity.OrderStatus;

public interface OrderService {
	
	OrderResponse createOrder(OrderRequest request);
	OrderResponse getOrderById(Long id);
	List<OrderResponse> getAllOrders();
	void updateOrderStatus(Long id, OrderStatus status);
	public List<OrderResponse> getMyOrders();
}
