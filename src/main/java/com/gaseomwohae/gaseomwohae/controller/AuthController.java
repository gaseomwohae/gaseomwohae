package com.gaseomwohae.gaseomwohae.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;
import com.gaseomwohae.gaseomwohae.service.AuthService;
import com.gaseomwohae.gaseomwohae.util.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(@AuthenticationPrincipal Long userId,
		@RequestBody LoginRequestDto loginRequestDto) {

		ResponseCookie cookie = authService.login(loginRequestDto);

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(ApiResponse.success());
	}

	@GetMapping("/cookietest")
	public Long test(@AuthenticationPrincipal Long userId) {
		return userId;
	}
}
