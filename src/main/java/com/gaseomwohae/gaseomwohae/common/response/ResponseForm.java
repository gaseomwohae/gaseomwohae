package com.gaseomwohae.gaseomwohae.common.response;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ResponseForm<T> {
	private final Integer code;
	private final Boolean success;
	private final String message;
	private final T data;

	private ResponseForm(Integer code, Boolean success, String message, T data) {
		this.code = code;
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ResponseForm<T> success(T data) {
		return new ResponseForm<>(200, true, "success", data);
	}

	public static <T> ResponseForm<T> success() {
		return new ResponseForm<>(200, true, "success", null);
	}

	public static <T> ResponseForm<T> error(ErrorCode errorCode) {
		return new ResponseForm<>(
			errorCode.getCode(),
			false,
			errorCode.getMessage(),
			null
		);
	}

	public static <T> ResponseForm<T> error(int code, String message) {
		return new ResponseForm<>(code, false, message, null);

	}
}
