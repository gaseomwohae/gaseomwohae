package com.gaseomwohae.gaseomwohae.common.exception.exceptions;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final Integer code;
	private final String message;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}
}
