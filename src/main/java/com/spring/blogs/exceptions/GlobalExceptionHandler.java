package com.spring.blogs.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		Map<String, String> response = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			defaultMessage = defaultMessage.substring(0, 1).toUpperCase() + defaultMessage.substring(1) + ".";
			response.put(field, defaultMessage);
		});

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
