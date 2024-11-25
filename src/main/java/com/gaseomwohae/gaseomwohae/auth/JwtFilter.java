package com.gaseomwohae.gaseomwohae.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.CustomException;
import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;
import com.gaseomwohae.gaseomwohae.model.User;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter
	extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		try {
			cookieUtil.extractAccessToken(request)
				.ifPresentOrElse(

					// 토큰이 있을때
					token -> {
						// 토큰 검증 (만료, 서버에서 발급했는지)
						jwtUtil.validateToken(token);

						// 유저 존재 여부 확인
						Long userId = jwtUtil.getUserIdFromToken(token);
						User user = userRepository.findById(userId);
						if (user == null) {
							throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
						}

						// 유저 정보 저장
						UsernamePasswordAuthenticationToken authentication =
							new UsernamePasswordAuthenticationToken(userId, null, null);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					},
					// 토큰이 없을때
					() -> {
						throw new BadRequestException(ErrorCode.TOKEN_NOT_FOUND);
					}
				);
			filterChain.doFilter(request, response);
		}
		// 필터단에서 발생하는 에러를 처리함 (예외처리)
		catch (CustomException e) {
			SecurityContextHolder.clearContext();
			response.setContentType("application/json");
			response.setStatus(e.getCode());
			response.getWriter().write(
				objectMapper.writeValueAsString(ResponseForm.error(e.getCode(), e.getMessage()))
			);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String[] excludePath = {"/api/auth/login", "/api/user/signup"};
		String path = request.getRequestURI();
		return Arrays.stream(excludePath).anyMatch(path::startsWith);
	}
}
