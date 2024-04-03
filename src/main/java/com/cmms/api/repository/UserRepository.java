package com.cmms.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Role;
import com.cmms.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * we use Optional for some fields because we overriding the default methods
	 * provided by spring security for username and email
	 */

	// find user by username
	Optional<User> findByUsername(String username);

	// find user by email
	Optional<User> findByEmail(String email);

	// find user by role
	List<User> findByRole(Role role);

}
