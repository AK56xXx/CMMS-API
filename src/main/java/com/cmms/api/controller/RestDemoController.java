package com.cmms.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.User;
import com.cmms.api.security.JwtService;
import com.cmms.api.service.IServiceUser;

@RestController
@RequestMapping("/app")
public class RestDemoController {

	@Autowired
	private IServiceUser iServiceUser;

	@Autowired
	private JwtService jwtService;


	@GetMapping("/demo")
	public ResponseEntity<String> demo() {
		return ResponseEntity.ok("Hello from secured URL");
	}

	@GetMapping("/admin_only")
	public ResponseEntity<String> adminOnly() {
		return ResponseEntity.ok("Hello this is secured URL for ADMIN only");
	}

	@GetMapping("/token/{token}")
	public Optional<User> demo(@PathVariable String token) {
		String username = jwtService.extractUsername(token);
		return iServiceUser.getUserByUsername(username);
	}

	

}
