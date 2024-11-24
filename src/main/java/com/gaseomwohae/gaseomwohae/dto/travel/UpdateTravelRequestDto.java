package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class UpdateTravelRequestDto {
	private String name;
	private String destination;
	private LocalDate startDate;
	private LocalDate endDate;
}
