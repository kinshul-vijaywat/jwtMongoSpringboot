package com.jwtLogin.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class GenericResponse {
	
	public GenericResponse() {
		
	}
	public GenericResponse(String message,String token, Boolean success, HttpStatus status) {
		this.message = message;
		this.token = token;
		this.success = success;
		this.status = status;
	}
	public GenericResponse(String message, Boolean success, HttpStatus status) {
		this.message = message;
		this.success = success;
		this.status = status;
	}
	private String message;
	private String token;
	private Boolean success;
	private HttpStatus status;
	
	

}
