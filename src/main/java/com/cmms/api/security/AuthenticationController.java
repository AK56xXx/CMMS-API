package com.cmms.api.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.User;


@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){

		return ResponseEntity.ok(authenticationService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User request) {
	    try {
	        AuthenticationResponse response = authenticationService.authenticate(request);
	        return ResponseEntity.ok(response); // Return successful response
	    } catch (AuthenticationException e) {
	        // Handle authentication failure
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("Invalid username or password");
	    }
	}
	
}




