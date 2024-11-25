package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateTravelRequestDto {
	private String name;
	private String destination;
	private LocalDate startDate;
	private LocalDate endDate;
	
	@Positive
	private Integer budget;
}
