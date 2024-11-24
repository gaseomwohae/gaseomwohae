package com.gaseomwohae.gaseomwohae.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaseomwohae.gaseomwohae.auth.CookieUtil;
import com.gaseomwohae.gaseomwohae.auth.JwtUtil;
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.dto.auth.LoginRequestDto;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;
import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;
import com.gaseomwohae.gaseomwohae.util.response.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	public ResponseCookie login(LoginRequestDto loginRequestDto) {
		String email = loginRequestDto.getEmail();
		String password = loginRequestDto.getPassword();

		User user = userRepository.findByEmail(email);

		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);
		}

		String accessToken = jwtUtil.createAccessToken(user.getId());
		String refreshToken = jwtUtil.createRefreshToken(user.getId());

		ResponseCookie accessTokenCookie = cookieUtil.createAccessToken(accessToken);
		ResponseCookie refreshTokenCookie = cookieUtil.createRefreshToken(accessToken);

		return accessTokenCookie;
	}

	@Override
	public ResponseCookie refreshAccessToken(String refreshToken) {
		return null;
	}
}
