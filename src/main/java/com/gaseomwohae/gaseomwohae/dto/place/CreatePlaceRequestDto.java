package com.gaseomwohae.gaseomwohae.dto.place;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePlaceRequestDto {
	@NotNull
	private Long id;
	@NotBlank
	private String name;

	private String category;

	private String address;

	private String roadAddress;

	private String thumbnail;

	private String phone;

	private String url;

	private BigDecimal y;

	private BigDecimal x;
}
