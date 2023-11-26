package com.jwtLogin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.jwtLogin.dto.GenericResponse;
import com.jwtLogin.dto.LoginDto;
import com.jwtLogin.security.JwtService;
import com.jwtLogin.security.User;

@Service
public class UserService{

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public GenericResponse loginUser(LoginDto loginDto) {

		String userDetails = loginDto.getTenantId() + User.SEP + loginDto.getUserId();
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, userDetails));
		User user = new User(userDetails);
		String token = jwtService.generateToken(getExtraClaims(user), user);
		return new GenericResponse("User Logged In Successfully", token, Boolean.TRUE, HttpStatus.OK);

	}

	private Map<String, Object> getExtraClaims(User user) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("tenantId", user.getTenantId());
		extraClaims.put("userId", user.getUserId());
		return extraClaims;
	}

	
}
