package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;
import com.gaseomwohae.gaseomwohae.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ResponseForm<Void>> login(@AuthenticationPrincipal Long userId,
		@RequestBody @Valid LoginRequestDto loginRequestDto) {

		return authService.login(loginRequestDto);
	}

	@GetMapping("/cookietest")
	public Long test(@AuthenticationPrincipal Long userId) {
		return userId;
	}
}
