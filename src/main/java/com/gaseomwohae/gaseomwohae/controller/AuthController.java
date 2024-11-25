package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;
import com.gaseomwohae.gaseomwohae.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

	@PostMapping("/refresh")
	public ResponseEntity<ResponseForm<Void>> refreshAccessToken(@AuthenticationPrincipal Long userId, HttpServletRequest request, HttpServletResponse response) {
		return authService.refreshAccessToken(userId, request, response);
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseForm<Void>> logout() {
		return authService.logout();
	}
}
