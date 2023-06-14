package com.jwtLogin.security;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtLogin.dto.RestError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		 RestError re = new RestError(HttpStatus.UNAUTHORIZED, "Authentication failed");
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        OutputStream responseStream = response.getOutputStream();
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(responseStream, re);
	        responseStream.flush();
		
	}
}