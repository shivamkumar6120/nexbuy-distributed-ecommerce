package com.nexbuy.payment.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.NOT_FOUND,
                "Order Not Found",
                ex.getMessage(),
                request);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(
            PaymentNotFoundException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.NOT_FOUND,
                "Payment Not Found",
                ex.getMessage(),
                request);
    }

    @ExceptionHandler(PaymentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePaymentExists(
            PaymentAlreadyExistsException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.CONFLICT,
                "Payment Already Exists",
                ex.getMessage(),
                request);
    }

    @ExceptionHandler(UnauthorizedPaymentAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedPaymentAccessException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                ex.getMessage(),
                request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request);
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        error,
                        message,
                        request.getRequestURI());

        return ResponseEntity
                .status(status)
                .body(response);
    }
}