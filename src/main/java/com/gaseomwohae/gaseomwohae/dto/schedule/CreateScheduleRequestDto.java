package com.gaseomwohae.gaseomwohae.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
	@NotNull
	private Long travelId;

	@NotNull
	private Long placeId;

	@NotNull
	private LocalDate date;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;

	@AssertTrue
	private boolean isValid() {
		return startTime.isBefore(endTime);
	}
}
