package com.gaseomwohae.gaseomwohae.util.response;

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
