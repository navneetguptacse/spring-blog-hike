package com.spring.blogs.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the response
public class ErrorResponse extends ApiResponse<Object> {

	private String errorCode;

	public ErrorResponse(String message, boolean success, String errorCode) {
		super(message, success, null);
		this.errorCode = errorCode;
	}
}
