package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTravelRequestDto {
	@NotBlank
	private String name;

	@NotBlank
	private String destination;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@AssertTrue()
	private boolean validateDateRange() {
		return !endDate.isBefore(startDate);
	}
}
