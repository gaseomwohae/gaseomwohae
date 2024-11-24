package com.gaseomwohae.gaseomwohae.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	INVALID_CREDENTIALS(401, "Invalid email or password"),
	UNAUTHORIZED(401, "Unauthorized access"),
	TOKEN_EXPIRED(401, "Token has expired"),
	INVALID_TOKEN(401, "Invalid token "),

	RESOURCE_NOT_FOUND(404, "Resource not found"),

	INVALID_INPUT(400, "Invalid input"),
	INTERNAL_SERVER_ERROR(500, "Internal server error"),

	DUPLICATE_EMAIL(400, "Duplicate email"),

	ACCESS_DENIED(403, "Access denied");

	private final Integer code;
	private final String message;
}
