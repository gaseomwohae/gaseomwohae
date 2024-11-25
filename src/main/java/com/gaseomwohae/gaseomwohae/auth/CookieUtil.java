package com.gaseomwohae.gaseomwohae.auth;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {
	public ResponseCookie createAccessToken(String token) {
		return ResponseCookie.from("access_token", token)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(30 * 60)
			.sameSite("None")
			.build();
	}

	public ResponseCookie createRefreshToken(String token) {
		return ResponseCookie.from("refresh_token", token)
			.httpOnly(true)
			.secure(true)
			.path("/api/auth/refresh")
			.maxAge(7 * 24 * 60 * 60)
			.sameSite("None")
			.build();
	}

	public ResponseCookie deleteAccessToken() {
		return ResponseCookie.from("access_token", "")
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(0)
			.sameSite("None")
			.build();
	}

	public ResponseCookie deleteRefreshToken() {
		return ResponseCookie.from("refresh_token", "")
			.httpOnly(true)
			.secure(true)
			.path("/api/auth/refresh")
			.maxAge(0)
			.sameSite("None")
			.build();
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		if (request.getCookies() == null)
			return Optional.empty();

		return Arrays.stream(request.getCookies())
			.filter(cookie -> "access_token".equals(cookie.getName()))
			.map(Cookie::getValue)
			.findFirst();
	}

	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		if (request.getCookies() == null)
			return Optional.empty();

		return Arrays.stream(request.getCookies())
			.filter(cookie -> "refresh_token".equals(cookie.getName()))
			.map(Cookie::getValue)
			.findFirst();
	}
}