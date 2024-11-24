package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;
import com.gaseomwohae.gaseomwohae.dto.user.SignUpRequestDto;
import com.gaseomwohae.gaseomwohae.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public void signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
		userService.signUp(signUpRequestDto);
	}

	@GetMapping()
	public GetUserInfoResponseDto getUserInfo(@AuthenticationPrincipal Long userId) {
		return userService.getUserInfo(userId);
	}
}
