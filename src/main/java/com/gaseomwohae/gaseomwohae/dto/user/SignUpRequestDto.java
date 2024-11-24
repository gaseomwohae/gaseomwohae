package com.gaseomwohae.gaseomwohae.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
	@NotBlank
	private String email;

	@NotBlank
	private String name;

	@NotBlank
	private String password;

	private String profileImage;
}
