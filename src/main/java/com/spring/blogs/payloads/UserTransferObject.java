package com.spring.blogs.payloads;

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
	private String name;
	private String email;
	private String username;
	private String password;
	private String about;
}
