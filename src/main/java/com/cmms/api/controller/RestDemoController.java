package com.cmms.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDemoController {

	@GetMapping("/demo")
	public ResponseEntity<String> demo(){
		return ResponseEntity.ok("Hello from secured URL");
	}
	
	@GetMapping("/admin_only")
	public ResponseEntity<String> adminOnly(){
		return ResponseEntity.ok("Hello this is secured URL for ADMIN only");
	}

}
