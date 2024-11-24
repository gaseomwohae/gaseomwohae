package com.gaseomwohae.gaseomwohae.service;

import org.springframework.http.ResponseCookie;

import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;

public interface AuthService {

	public ResponseCookie login(LoginRequestDto loginRequestDto);

	public ResponseCookie refreshAccessToken(String refreshToken);

}
