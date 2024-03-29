package com.cmms.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.User;
import com.cmms.api.exception.user.EmptyFieldException;
import com.cmms.api.exception.user.UsernameExistException;
import com.cmms.api.repository.UserRepository;

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

		String token = jwtService.generateToken(user);

		return new AuthenticationResponse(token);

	}

	public AuthenticationResponse authenticate(User request) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

		String jwt = jwtService.generateToken(user);

		return new AuthenticationResponse(jwt);

	}

	// logout
	public void blacklistToken(String token) {
		jwtService.addToBlacklist(token);
	}

}
