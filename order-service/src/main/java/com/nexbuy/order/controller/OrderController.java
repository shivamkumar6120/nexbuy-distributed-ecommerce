package com.nexbuy.order.controller;

import com.nexbuy.order.dto.OrderRequest;
import com.nexbuy.order.dto.OrderResponse;
import com.nexbuy.order.entity.OrderStatus;
import com.nexbuy.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {

		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
	}

	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("/my-orders")
	public ResponseEntity<List<OrderResponse>> getMyOrders() {

		return ResponseEntity.ok(orderService.getMyOrders());
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {

		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
		orderService.updateOrderStatus(id, status);
		return ResponseEntity.noContent().build();
	}

}