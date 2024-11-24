package com.gaseomwohae.gaseomwohae.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;
import com.gaseomwohae.gaseomwohae.util.response.exceptions.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	private final long ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000L;
	private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L;

	private Key key;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(Long userId) {
		return createToken(userId, ACCESS_TOKEN_VALIDITY);
	}

	public String createRefreshToken(Long userId) {
		return createToken(userId, REFRESH_TOKEN_VALIDITY);
	}

	private String createToken(Long userId, long validity) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + validity);

		return Jwts.builder()
			.claim("sub", String.valueOf(userId))
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException ex) {
			throw new BadRequestException(ErrorCode.TOKEN_EXPIRED);
		} catch (JwtException | IllegalArgumentException ex) {
			throw new BadRequestException(ErrorCode.INVALID_TOKEN);
		}
	}
}