package com.cmms.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.User;

import jakarta.security.auth.message.config.AuthConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {

		return ResponseEntity.ok(authenticationService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User request, HttpServletResponse cookiResponse) {
		try {
			AuthenticationResponse response = authenticationService.authenticate(request, cookiResponse);
			return ResponseEntity.ok(response); // Return successful response
		} catch (AuthenticationException e) {
			// Handle authentication failure
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid username or password");
		}
	}

	@GetMapping("/login_menu")
	public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response) {

		return ResponseEntity.status(HttpStatus.OK)
				.body("redirected to login menu");

	}





	
	// LOGIN REACT NATIVE //

	@PostMapping("/login_rn")
	public ResponseEntity<?> loginRN(@RequestBody User request) {
        try {
            AuthenticationResponse response = authenticationService.authenticateMobile(request);
            return ResponseEntity.ok(response); // Return successful response
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }



}
