package com.spring.blogs.exceptions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String resourceFieldName;
	Integer resourcefieldValue;

	public ResourceNotFoundException(String resourceName, String resourceFieldName, Integer resourcefieldValue) {
		super(String.format("%s not found with %s %d", resourceName, resourceFieldName.toLowerCase(),
				resourcefieldValue));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.resourcefieldValue = resourcefieldValue;
	}
}
