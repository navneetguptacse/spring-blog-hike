package com.spring.blogs.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.models.UserEntity;
import com.spring.blogs.payloads.UserTransferObject;
import com.spring.blogs.repositories.UserRepository;
import com.spring.blogs.services.UserService;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserTransferObject createUser(UserTransferObject userTransferObject) {
		UserEntity user = this.transferObjectToUser(userTransferObject);
		UserEntity saved = this.userRepository.save(user);
		return this.userToTransferObject(saved);
	}

	@Override
	public UserTransferObject updateUser(UserTransferObject userTransferObject, Integer userId) {
		UserEntity user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userTransferObject.getName());
		user.setEmail(userTransferObject.getEmail());
		user.setPassword(userTransferObject.getPassword());
		user.setAbout(userTransferObject.getAbout());
		user.setUsername(userTransferObject.getUsername());
		user = this.userRepository.save(user);
		return this.userToTransferObject(user);
	}

	@Override
	public UserTransferObject getUserById(Integer userId) {
		UserEntity user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.userToTransferObject(user);
	}

	@Override
	public List<UserTransferObject> getAllUsers() {
		List<UserEntity> userList = this.userRepository.findAll();
		List<UserTransferObject> users = userList.stream().map(user -> this.userToTransferObject(user))
				.collect(Collectors.toList());
		return users;
	}

	@Override
	public void deleteUser(Integer userId) {
		UserEntity user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepository.delete(user);
	}

	@Override
	public Boolean checkUserExistsByEmailOrUsername(String email, String username) {
		boolean emailExists = this.userRepository.findByEmail(email).isPresent();
		boolean usernameExists = userRepository.findByUsername(username).isPresent();

		return emailExists || usernameExists;
	}

	// Method: Converting UserTransferObject to UserEntity
	public UserEntity transferObjectToUser(UserTransferObject userTransferObject) {
		UserEntity user = new UserEntity();
		user.setId(userTransferObject.getId());
		user.setName(userTransferObject.getName());
		user.setEmail(userTransferObject.getEmail());
		user.setUsername(userTransferObject.getUsername());
		user.setPassword(userTransferObject.getPassword());
		user.setAbout(userTransferObject.getAbout());
		return user;
	}

	// Method: UserEntity to Converting UserTransferObject
	public UserTransferObject userToTransferObject(UserEntity user) {
		UserTransferObject userTransferObject = new UserTransferObject();
		userTransferObject.setId(user.getId());
		userTransferObject.setName(user.getName());
		userTransferObject.setEmail(user.getEmail());
		userTransferObject.setUsername(user.getUsername());
		userTransferObject.setPassword(user.getPassword());
		userTransferObject.setAbout(user.getAbout());
		return userTransferObject;
	}

}
