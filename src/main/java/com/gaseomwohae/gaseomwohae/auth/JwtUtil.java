package com.gaseomwohae.gaseomwohae.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	// Access Token 유효시간: 30분
	private final long ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000L;

	// Refresh Token 유효시간: 7일
	private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L;

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
			.setSubject(String.valueOf(userId))
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty");
		}
		return false;
	}
}