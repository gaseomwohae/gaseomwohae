package com.gaseomwohae.gaseomwohae.dto.schedule;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private LocalTime startTime;
    private LocalTime endTime;
}
