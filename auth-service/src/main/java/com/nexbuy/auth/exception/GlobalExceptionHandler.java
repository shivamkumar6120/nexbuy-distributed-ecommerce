package com.nexbuy.auth.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
        	    LocalDateTime.now(),
        	    HttpStatus.CONFLICT.value(),
        	    HttpStatus.CONFLICT.getReasonPhrase(),
        	    ex.getMessage(),
        	    request.getRequestURI()
        	);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}