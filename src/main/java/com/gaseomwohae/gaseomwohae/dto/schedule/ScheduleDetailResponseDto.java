package com.gaseomwohae.gaseomwohae.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.gaseomwohae.gaseomwohae.model.Place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleDetailResponseDto {
    private Long scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Place place;
}
