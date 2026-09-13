package com.nexbuy.payment.exception;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(String message) {
		super(message);
	}
}