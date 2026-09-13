package com.nexbuy.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexbuy.payment.dto.PaymentRequest;
import com.nexbuy.payment.dto.PaymentResponse;
import com.nexbuy.payment.service.PaymentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.processPayment(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(
                        orderId));
    }
}