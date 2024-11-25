package com.spring.blogs.services;

import java.util.List;

import com.spring.blogs.payloads.UserTransferObject;

public interface UserService {
	UserTransferObject createUser(UserTransferObject user);

	Boolean checkUserExistsByEmailOrUsername(String email, String username);

	UserTransferObject updateUser(UserTransferObject user, Integer userId);

	UserTransferObject getUserById(Integer userId);

	List<UserTransferObject> getAllUsers();

	void deleteUser(Integer userId);
}
