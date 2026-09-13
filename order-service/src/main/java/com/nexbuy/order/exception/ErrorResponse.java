package com.nexbuy.order.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;

}
