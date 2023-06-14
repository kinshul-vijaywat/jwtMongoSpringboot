package com.jwtLogin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jwtLogin.dto.GenericResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<GenericResponse> handleBindException(BindException e){
		return ResponseEntity.badRequest().body(new GenericResponse(e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage(),Boolean.FALSE,HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<GenericResponse> handleBadCredentialsException(BadCredentialsException e){
		return ResponseEntity.badRequest().body(new GenericResponse(e.getMessage(),Boolean.FALSE,HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		if(e.getFieldError() != null) {
			return ResponseEntity.badRequest().body(new GenericResponse(e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage(),Boolean.FALSE,HttpStatus.BAD_REQUEST));
		}
		return ResponseEntity.badRequest().body(new GenericResponse(e.getMessage(),Boolean.FALSE,HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<GenericResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		return ResponseEntity.badRequest().body(new GenericResponse(e.getMessage(),Boolean.FALSE,HttpStatus.BAD_REQUEST));
	}
}
