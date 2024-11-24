package com.gaseomwohae.gaseomwohae.util.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private final Integer code;
	private final Boolean success;
	private final String message;
	private final T data;

	private ApiResponse(Integer code, Boolean success, String message, T data) {
		this.code = code;
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(200, true, "success", data);

	}

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(200, true, "", null);
	}

	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return new ApiResponse<>(
			errorCode.getCode(),
			false,
			errorCode.getMessage(),
			null
		);
	}

	public static <T> ApiResponse<T> error(int code, String message) {
		return new ApiResponse<>(code, false, message, null);

	}
}
