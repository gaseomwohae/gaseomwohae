package com.gaseomwohae.gaseomwohae.service;

import org.springframework.http.ResponseEntity;

import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	public ResponseEntity<ResponseForm<Void>> login(LoginRequestDto loginRequestDto);

	public ResponseEntity<ResponseForm<Void>> refreshAccessToken(Long userId, HttpServletRequest request, HttpServletResponse response);

	public ResponseEntity<ResponseForm<Void>> logout();
}
