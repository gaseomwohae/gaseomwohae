package com.gaseomwohae.gaseomwohae.dto.schedule;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleListResponseDto {
	private LocalDate date;
	private ScheduleDetailResponseDto[] schedule;
}
