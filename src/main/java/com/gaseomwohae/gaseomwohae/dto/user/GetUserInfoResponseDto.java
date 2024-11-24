package com.gaseomwohae.gaseomwohae.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoResponseDto {
	private String name;
	private String email;
	private String profileImage;
}
