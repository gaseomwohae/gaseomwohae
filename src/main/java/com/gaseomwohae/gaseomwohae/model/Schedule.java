package com.gaseomwohae.gaseomwohae.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Schedule {
	private Long id;
	private Long travelId;
	private Long placeId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
} 