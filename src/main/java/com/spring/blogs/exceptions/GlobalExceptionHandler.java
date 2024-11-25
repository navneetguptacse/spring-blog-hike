package com.spring.blogs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.blogs.responses.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
		String message = e.getMessage();
		ApiResponse<Object> response = new ApiResponse<>(message, false, null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
