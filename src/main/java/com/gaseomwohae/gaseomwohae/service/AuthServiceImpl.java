package com.gaseomwohae.gaseomwohae.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.auth.CookieUtil;
import com.gaseomwohae.gaseomwohae.auth.JwtUtil;
import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	public ResponseEntity<ResponseForm<Void>> login(LoginRequestDto loginRequestDto) {
		String email = loginRequestDto.getEmail();
		String password = loginRequestDto.getPassword();

		User user = userRepository.findByEmail(email);

		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);
		}

		String accessToken = jwtUtil.createAccessToken(user.getId());
		String refreshToken = jwtUtil.createRefreshToken(user.getId());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookieUtil.createAccessToken(accessToken).toString())
			.header(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshToken(refreshToken).toString())
			.body(ResponseForm.success());

	}

	@Override
	public ResponseEntity<ResponseForm<Void>> refreshAccessToken(String refreshToken) {
		return null;
	}
}
