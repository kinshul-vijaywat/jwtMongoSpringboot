package com.jwtLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtLogin.dto.GenericResponse;
import com.jwtLogin.dto.LoginDto;
import com.jwtLogin.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<GenericResponse> loginUser(@Valid @RequestBody LoginDto loginDto) {		
		GenericResponse loginUser = userService.loginUser(loginDto);
		return ResponseEntity.status(loginUser.getStatus()).body(loginUser);
	}
	
	@GetMapping("/securedData")
	public String getSecuredData () {		
		return "This is protected Data";
	}
}
