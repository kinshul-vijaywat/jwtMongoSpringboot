package com.jwtLogin.security;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtLogin.dto.RestError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		 RestError re = new RestError(HttpStatus.FORBIDDEN, "You dont have enough priviledges");
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        OutputStream responseStream = response.getOutputStream();
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(responseStream, re);
	        responseStream.flush();	
	}

}
