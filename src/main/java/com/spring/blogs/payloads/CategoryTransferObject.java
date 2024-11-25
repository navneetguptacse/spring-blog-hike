package com.spring.blogs.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTransferObject {
	private Integer id;

	@NotEmpty(message = "Title must not be empty")
	@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
	private String title;

	// This field is Optional, as we can update it later.
	@Size(max = 200, message = "Description cannot be longer than 200 characters")
	private String description;

}
