package com.nexbuy.payment.service;

import com.nexbuy.payment.dto.PaymentRequest;
import com.nexbuy.payment.dto.PaymentResponse;

public interface PaymentService {

	PaymentResponse processPayment(PaymentRequest request);

	PaymentResponse getPaymentByOrderId(Long orderId);
}