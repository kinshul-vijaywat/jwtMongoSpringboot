package com.jwtLogin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDto {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9@]*$", message = "can contain only alphabets and numbers with @ symbol")
	private String userId;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9@]*$", message = "can contain only alphabets and numbers with @ symbol")
	private String tenantId;
	
}
