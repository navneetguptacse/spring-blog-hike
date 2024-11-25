package com.spring.blogs.controllers;

import com.spring.blogs.payloads.UserTransferObject;
import com.spring.blogs.responses.ApiResponse;
import com.spring.blogs.responses.ErrorResponse;
import com.spring.blogs.services.UserService;
import com.spring.blogs.exceptions.ResourceNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST: Create User
	@PostMapping("/")
	public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody UserTransferObject userTransferObject) {
		try {
			boolean userExists = this.userService.checkUserExistsByEmailOrUsername(userTransferObject.getEmail(),
					userTransferObject.getUsername());

			if (userExists) {
				ErrorResponse errorResponse = new ErrorResponse("User already exists with the same email or username",
						false, "USER_ALREADY_EXISTS");
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			}

			UserTransferObject createdUserTransferObject = this.userService.createUser(userTransferObject);

			ApiResponse<Object> response = new ApiResponse<>("User created successfully", true,
					createdUserTransferObject);
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed to create user", false, null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// PUT: Update User
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody UserTransferObject userTransferObject,
			@PathVariable Integer userId) {
		try {
			UserTransferObject updatedUserTransferObject = this.userService.updateUser(userTransferObject, userId);
			ApiResponse<Object> response = new ApiResponse<>("User updated successfully", true,
					updatedUserTransferObject);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "USER_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// DELETE: Delete User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Integer userId) {
		try {
			this.userService.deleteUser(userId);
			ApiResponse<Object> response = new ApiResponse<>("User deleted successfully", true, null);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "USER_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// GET: Get User by Id
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable Integer userId) {
		try {
			UserTransferObject userTransferObject = this.userService.getUserById(userId);

			ApiResponse<Object> response = new ApiResponse<>("User fetched successfully", true, userTransferObject);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "USER_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// GET: Get All Users
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<UserTransferObject>>> getAllUsers() {
		List<UserTransferObject> userTransferObjects = this.userService.getAllUsers();
		if (userTransferObjects.isEmpty()) {
			ApiResponse<List<UserTransferObject>> response = new ApiResponse<>("Users not found", true, null);
			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		}

		ApiResponse<List<UserTransferObject>> response = new ApiResponse<>("Users fetched successfully", true,
				userTransferObjects);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
