package com.gaseomwohae.gaseomwohae.util.response.exceptions;

import com.gaseomwohae.gaseomwohae.util.response.CustomException;
import com.gaseomwohae.gaseomwohae.util.response.ErrorCode;

public class BadRequestException extends CustomException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
