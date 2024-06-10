package com.cmms.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmms.api.entity.User;
import com.cmms.api.security.JwtService;
import com.cmms.api.service.IServiceUser;
import com.cmms.api.service.cloud.CloudinaryService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class RestDemoController {

	@Autowired
	private IServiceUser iServiceUser;

	@Autowired
	private JwtService jwtService;

	@Autowired
    private CloudinaryService cloudinaryService;

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

	@PostMapping("/upload")
    public ResponseEntity<Map> upload(@RequestParam("file") MultipartFile file) {
        try {
            Map result = cloudinaryService.upload(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
