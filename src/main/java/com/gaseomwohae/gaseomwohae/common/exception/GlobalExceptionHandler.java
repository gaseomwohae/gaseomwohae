package com.gaseomwohae.gaseomwohae.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.gaseomwohae.gaseomwohae.common.exception.exceptions.CustomException;
import com.gaseomwohae.gaseomwohae.common.response.ResponseForm;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ResponseForm<String>> handleNoResourceFoundException(NoResourceFoundException e) {
		ErrorCode errorCode = ErrorCode.RESOURCE_NOT_FOUND;
		Integer code = errorCode.getCode();
		String message = errorCode.getMessage();
		return ResponseEntity.status(code).body(ResponseForm.error(code, message));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseForm<String>> handleValidationException(MethodArgumentNotValidException e) {
		ErrorCode errorCode = ErrorCode.INVALID_INPUT;
		Integer code = errorCode.getCode();
		String message = errorCode.getMessage();
		return ResponseEntity.status(code).body(ResponseForm.error(code, message));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ResponseForm<String>> handleCustomException(CustomException e) {
		return ResponseEntity.status(e.getCode())
			.body(ResponseForm.error(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseForm<String>> handleOtherException(Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		Integer code = errorCode.getCode();
		String message = errorCode.getMessage();
		return ResponseEntity.status(code).body(ResponseForm.error(code, message));
	}
}
