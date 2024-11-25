package com.spring.blogs.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTransferObject {
	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "Name field should be min of 4 characters")
	private String name;

	@Email(message = "Invalid email address format")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|org|net|edu|gov|mil|co|[a-zA-Z]{2})$", message = "Invalid email domain. Allowed domains: .com, .org, .net, .edu, .gov, .mil, .co, and valid country TLDs")
	private String email;

	@NotEmpty
	@Size(min = 6, max = 24, message = "Username must be between 4 and 15 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
	private String username;

	@NotEmpty
	@Size(min = 8, max = 16, message = "Password should be between 8 and 16 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;

	@NotEmpty
	private String about;
}
