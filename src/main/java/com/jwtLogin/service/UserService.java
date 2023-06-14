package com.jwtLogin.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.jwtLogin.dto.GenericResponse;
import com.jwtLogin.dto.LoginDto;
import com.jwtLogin.dto.UserDto;
import com.jwtLogin.entity.User;
import com.jwtLogin.repository.UserRepository;
import com.jwtLogin.security.JwtService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public GenericResponse registerUser(UserDto userDto) {
		
		if(userRepository.findByUserId(userDto.getUserId()).isPresent()) {
			System.out.println("User already exists");
			return new GenericResponse("User already exists",Boolean.FALSE,HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setApiKey(userDto.getApiKey());
		user.setTenantId(userDto.getTenantId());
		user.setCurrentToken(null);
		user.setId(UUID.randomUUID().toString());
		userRepository.save(user);
		return new GenericResponse("User registered Successfully",Boolean.TRUE,HttpStatus.OK);		
	}

	public GenericResponse loginUser(LoginDto loginDto) {
		Optional<User> userOpt = userRepository.findByUserId(loginDto.getUserId());
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getApiKey()));
			String token = jwtService.generateToken(getExtraClaims(user),user);
			user.setCurrentToken(token);
			userRepository.save(user);
			return new GenericResponse("User Logged In Successfully",user.getCurrentToken(),Boolean.TRUE,HttpStatus.OK);
			
		}
		
		return new GenericResponse("User not exists",Boolean.FALSE,HttpStatus.BAD_REQUEST);
	}

	private Map<String, Object> getExtraClaims(User user){
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("tenantId", user.getTenantId());
		return extraClaims;
	}
}
