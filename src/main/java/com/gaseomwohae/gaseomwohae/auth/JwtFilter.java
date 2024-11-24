package com.gaseomwohae.gaseomwohae.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaseomwohae.gaseomwohae.dto.User;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;
import com.gaseomwohae.gaseomwohae.util.response.ApiResponse;
import com.gaseomwohae.gaseomwohae.util.response.CustomException;
import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;
import com.gaseomwohae.gaseomwohae.util.response.exceptions.BadRequestException;

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
				.ifPresent(token -> {
					jwtUtil.validateToken(token);
					Long userId = jwtUtil.getUserIdFromToken(token);
					User user = userRepository.findById(userId);
					if (user == null) {
						throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
					}
					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userId, null, null);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				});

			filterChain.doFilter(request, response);
		} catch (CustomException e) {
			SecurityContextHolder.clearContext();
			response.setContentType("application/json");
			response.setStatus(e.getCode());
			response.getWriter().write(
				objectMapper.writeValueAsString(ApiResponse.error(e.getCode(), e.getMessage()))
			);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String[] excludePath = {"/api/auth/login"};
		String path = request.getRequestURI();
		return Arrays.stream(excludePath).anyMatch(path::startsWith);
	}
}
