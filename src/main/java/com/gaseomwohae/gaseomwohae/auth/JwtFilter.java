package com.gaseomwohae.gaseomwohae.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		try {
			cookieUtil.extractAccessToken(request)
				.filter(jwtUtil::validateToken)
				.ifPresent(token -> {
					Long userId = jwtUtil.getUserIdFromToken(token);
					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userId, null, null);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				});

		} catch (Exception e) {
			log.error("Could not set user authentication in security context", e);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String[] excludePath = {"/api/auth/login"};
		String path = request.getRequestURI();
		return Arrays.stream(excludePath).anyMatch(path::startsWith);
	}
}
