package com.gaseomwohae.gaseomwohae.dto.place;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePlaceRequestDto {
    @NotBlank
	private String name;
    @NotBlank
	private String category;
    @NotBlank
	private String address;
    @NotBlank
	private String roadAddress;
    @NotBlank
	private String thumbnail;
    @NotBlank
	private String phone;
    @NotBlank
	private String url;
    @NotNull
	private BigDecimal x;
    @NotNull
	private BigDecimal y;
}
