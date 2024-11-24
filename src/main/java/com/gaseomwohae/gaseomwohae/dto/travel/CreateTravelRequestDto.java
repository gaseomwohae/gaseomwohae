package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
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
	@FutureOrPresent
	private LocalDate startDate;

	@NotNull
	@FutureOrPresent
	private LocalDate endDate;

	@AssertTrue()
	private boolean validateDateRange() {
		return !endDate.isBefore(startDate);
	}
}
