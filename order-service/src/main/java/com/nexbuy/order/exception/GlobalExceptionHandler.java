package com.nexbuy.order.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientStock(InsufficientStockException ex,
			HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 
				HttpStatus.CONFLICT.value(), 
				"Insufficient Stock",
				ex.getMessage(), 
				request.getRequestURI());

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(error);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleErrorNotFound(
			OrderNotFoundException ex,
	        HttpServletRequest request){
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            "Order Not Found",
	            ex.getMessage(),
	            request.getRequestURI());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
	
	
}
























