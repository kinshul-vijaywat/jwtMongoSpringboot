package com.jwtLogin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

	@NotBlank
	private String userId;
	
	@NotBlank
	private String apiKey;
	
}
