package com.cmms.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Role;
import com.cmms.api.entity.User;
import com.cmms.api.repository.UserRepository;

@Service
public class ServiceUser implements IServiceUser {

	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("null")
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findUserById(int id) {
		return userRepository.findById(id).get();
	}

	@SuppressWarnings("null")
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@SuppressWarnings("null")
	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);

	}

	// find user by technician role
	@Override
	public List<User> getAllTechnicians() {
		return userRepository.findByRole(Role.TECHNICIAN);
	}

	@Override
	public List<User> getAllClients() {
		return userRepository.findByRole(Role.CLIENT);
	}

}
