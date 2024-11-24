package com.gaseomwohae.gaseomwohae.common.exception.exceptions;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;

public class BadRequestException extends CustomException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
