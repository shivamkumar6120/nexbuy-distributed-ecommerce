package com.nexbuy.order.service;

import com.nexbuy.order.repository.OrderItemRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nexbuy.order.client.ProductClient;
import com.nexbuy.order.dto.OrderItemRequest;
import com.nexbuy.order.dto.OrderItemResponse;
import com.nexbuy.order.dto.OrderRequest;
import com.nexbuy.order.dto.OrderResponse;
import com.nexbuy.order.dto.ProductResponse;
import com.nexbuy.order.entity.Order;
import com.nexbuy.order.entity.OrderItem;
import com.nexbuy.order.entity.OrderStatus;
import com.nexbuy.order.exception.InsufficientStockException;
import com.nexbuy.order.exception.InvalidOrderStatusException;
import com.nexbuy.order.exception.OrderNotFoundException;
import com.nexbuy.order.exception.UnauthorizedOrderAccessException;
import com.nexbuy.order.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final ProductClient productClient;

	@Override
	public OrderResponse createOrder(OrderRequest request) {

		BigDecimal totalAmount = BigDecimal.ZERO;

		List<ProductResponse> products = new ArrayList<>();

		// 1. Validate all products and stock first
		for (OrderItemRequest itemRequest : request.getItems()) {

			ProductResponse product = productClient.getProductById(itemRequest.getProductId());

			if (product.getStockQuantity() < itemRequest.getQuantity()) {

				throw new InsufficientStockException("Insufficient stock for product id: " + product.getId());
			}

			products.add(product);
		}

		// 2. Create Order only after stock validation
		Order order = new Order();

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setUserEmail(email);
		order.setStatus(OrderStatus.CREATED);
		order.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(order);

		List<OrderItemResponse> itemResponses = new ArrayList<>();

		// 3. Reduce stock and save OrderItems
		for (int i = 0; i < request.getItems().size(); i++) {

			OrderItemRequest itemRequest = request.getItems().get(i);

			ProductResponse product = products.get(i);

			productClient.reduceStock(product.getId(), itemRequest.getQuantity());

			BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

			totalAmount = totalAmount.add(itemTotal);

			OrderItem orderItem = new OrderItem();

			orderItem.setOrderId(savedOrder.getId());
			orderItem.setProductId(product.getId());
			orderItem.setQuantity(itemRequest.getQuantity());
			orderItem.setPrice(product.getPrice());

			orderItemRepository.save(orderItem);

			itemResponses.add(new OrderItemResponse(product.getId(), itemRequest.getQuantity(), product.getPrice()));
		}

		// 4. Update total
		savedOrder.setTotalAmount(totalAmount);

		savedOrder = orderRepository.save(savedOrder);

		return new OrderResponse(savedOrder.getId(), savedOrder.getUserEmail(), savedOrder.getTotalAmount(),
				savedOrder.getStatus(), savedOrder.getCreatedAt(), itemResponses);
	}

	@Override
	public OrderResponse getOrderById(Long id) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		if (!order.getUserEmail().equals(userEmail)) {
			throw new UnauthorizedOrderAccessException("You are not allowed to access this order");
		}

		List<OrderItem> items = orderItemRepository.findByOrderId(id);

		List<OrderItemResponse> itemResponses = items.stream()
				.map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getPrice())).toList();

		return new OrderResponse(order.getId(), order.getUserEmail(), order.getTotalAmount(), order.getStatus(),
				order.getCreatedAt(), itemResponses);
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		List<Order> orders = orderRepository.findAll();

		return orders.stream().map(order -> {
			List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
			List<OrderItemResponse> itemResponses = items.stream()
					.map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getPrice()))
					.toList();
			return new OrderResponse(order.getId(), order.getUserEmail(), order.getTotalAmount(), order.getStatus(),
					order.getCreatedAt(), itemResponses);
		}).toList();
	}

	@Override
	public void updateOrderStatus(Long id, OrderStatus status) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		if (!order.getUserEmail().equals(userEmail)) {
			throw new UnauthorizedOrderAccessException("You are not allowed to modify this order");
		}

		if (order.getStatus() != OrderStatus.CREATED) {
			throw new InvalidOrderStatusException("Order status cannot be changed from " + order.getStatus());
		}

		if (status == OrderStatus.CANCELLED) {

			List<OrderItem> items = orderItemRepository.findByOrderId(id);

			for (OrderItem item : items) {

				productClient.increaseStock(item.getProductId(), item.getQuantity());
			}
		}

		order.setStatus(status);

		orderRepository.save(order);
	}

	@Override
	public List<OrderResponse> getMyOrders() {

		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Order> orders = orderRepository.findByUserEmail(userEmail);

		return orders.stream().map(order -> {

			List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

			List<OrderItemResponse> itemResponses = items.stream()
					.map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getPrice()))
					.toList();

			return new OrderResponse(order.getId(), order.getUserEmail(), order.getTotalAmount(), order.getStatus(),
					order.getCreatedAt(), itemResponses);
		}).toList();
	}

}
