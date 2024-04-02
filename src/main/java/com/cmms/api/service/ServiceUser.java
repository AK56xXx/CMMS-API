package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
