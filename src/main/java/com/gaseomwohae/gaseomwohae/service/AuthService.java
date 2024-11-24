package com.gaseomwohae.gaseomwohae.service;

import org.springframework.http.ResponseEntity;

import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;

public interface AuthService {

	public ResponseEntity<ResponseForm<Void>> login(LoginRequestDto loginRequestDto);

	public ResponseEntity<ResponseForm<Void>> refreshAccessToken(String refreshToken);

}
