package com.nexbuy.payment.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nexbuy.payment.client.OrderClient;
import com.nexbuy.payment.dto.OrderResponse;
import com.nexbuy.payment.dto.PaymentRequest;
import com.nexbuy.payment.dto.PaymentResponse;
import com.nexbuy.payment.entity.Payment;
import com.nexbuy.payment.entity.PaymentStatus;
import com.nexbuy.payment.exception.OrderNotFoundException;
import com.nexbuy.payment.exception.PaymentAlreadyExistsException;
import com.nexbuy.payment.exception.PaymentNotFoundException;
import com.nexbuy.payment.exception.UnauthorizedPaymentAccessException;
import com.nexbuy.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final OrderClient orderClient;

	@Override
	public PaymentResponse processPayment(PaymentRequest request) {

		String userEmail = getCurrentUserEmail();

		OrderResponse order;

		try {

			order = orderClient.getOrderById(request.getOrderId());

		} catch (Exception ex) {

			throw new OrderNotFoundException("Order not found with id: " + request.getOrderId());
		}

		if (!order.getUserEmail().equals(userEmail)) {

			throw new UnauthorizedPaymentAccessException("You are not allowed to pay for this order");
		}

		if (!order.getTotalAmount().equals(request.getAmount())) {

			throw new IllegalArgumentException("Payment amount does not match order amount");
		}

		if (order.getStatus() != com.nexbuy.payment.entity.OrderStatus.CREATED) {

			throw new IllegalArgumentException("Payment can only be made for CREATED orders");
		}

		if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {

			throw new PaymentAlreadyExistsException("Payment already exists for order id: " + request.getOrderId());
		}

		Payment payment = new Payment();

		payment.setOrderId(order.getId());
		payment.setUserEmail(userEmail);
		payment.setAmount(request.getAmount());
		payment.setStatus(PaymentStatus.SUCCESS);
		payment.setCreatedAt(LocalDateTime.now());

		Payment savedPayment = paymentRepository.save(payment);

		orderClient.confirmOrder(order.getId());

		return toResponse(savedPayment);
	}

	@Override
	public PaymentResponse getPaymentByOrderId(Long orderId) {

		String userEmail = getCurrentUserEmail();

		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found for order id: " + orderId));

		if (!payment.getUserEmail().equals(userEmail)) {

			throw new UnauthorizedPaymentAccessException("You are not allowed to access this payment");
		}

		return toResponse(payment);
	}

	private String getCurrentUserEmail() {

		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	private PaymentResponse toResponse(Payment payment) {

		return new PaymentResponse(payment.getId(), payment.getOrderId(), payment.getUserEmail(), payment.getAmount(),
				payment.getStatus(), payment.getCreatedAt());
	}
}