package com.cmms.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

}
