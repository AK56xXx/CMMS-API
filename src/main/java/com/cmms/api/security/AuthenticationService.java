package com.cmms.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.User;
import com.cmms.api.exception.user.EmptyFieldException;
import com.cmms.api.exception.user.UsernameExistException;
import com.cmms.api.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationResponse register(User request) {

		if (request.getUsername().isBlank() || request.getPassword().isBlank()) {
			throw new EmptyFieldException();
		} else if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new UsernameExistException();

		}

		User user = new User();

		user.setFname(request.getFname());
		user.setLname(request.getLname());

		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		user = userRepository.save(user);

		// String token = jwtService.generateToken(user);

		return new AuthenticationResponse("require login");

	}

	public AuthenticationResponse authenticate(User request, HttpServletResponse response) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

		String token = jwtService.generateToken(user);

		// Create a cookie
		Cookie cookie = new Cookie("accesstoken", token);
		cookie.setHttpOnly(true); // Make the cookie accessible only by the server
		cookie.setMaxAge(-1); // Make the cookie expire when the browser session ends
		cookie.setPath("/"); // Set the cookie path to the root
		cookie.setValue(token);
		// Add the cookie to the response
		response.addCookie(cookie);

		return new AuthenticationResponse(token);

	}

}
