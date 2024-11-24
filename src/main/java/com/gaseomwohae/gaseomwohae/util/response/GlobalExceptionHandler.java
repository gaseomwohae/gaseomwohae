package com.gaseomwohae.gaseomwohae.util.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException e) {
		ErrorCode errorCode = ErrorCode.INVALID_INPUT;
		Integer code = errorCode.getCode();
		String message = errorCode.getMessage();
		return ResponseEntity.status(code).body(ApiResponse.error(code, message));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
		return ResponseEntity.status(e.getCode())
			.body(ApiResponse.error(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleOtherException(Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		Integer code = errorCode.getCode();
		String message = errorCode.getMessage();
		return ResponseEntity.status(code).body(ApiResponse.error(code, message));
	}
}
