package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.user.SignUpRequestDto;
import com.gaseomwohae.gaseomwohae.service.UserService;
import com.gaseomwohae.gaseomwohae.util.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
		userService.signUp(signUpRequestDto);
		return ResponseEntity.ok(ApiResponse.success());
	}
}
