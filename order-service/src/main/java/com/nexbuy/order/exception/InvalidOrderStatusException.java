package com.nexbuy.order.exception;

public class InvalidOrderStatusException extends RuntimeException {
	public InvalidOrderStatusException(String msg) {
		super(msg);
	}

}
