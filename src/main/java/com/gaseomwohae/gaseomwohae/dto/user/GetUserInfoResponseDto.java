package com.gaseomwohae.gaseomwohae.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GetUserInfoResponseDto {
	private String name;
	private String email;
	private String profileImage;
	private LocalDateTime createdAt;
}
