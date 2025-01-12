package com.gaseomwohae.gaseomwohae.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
