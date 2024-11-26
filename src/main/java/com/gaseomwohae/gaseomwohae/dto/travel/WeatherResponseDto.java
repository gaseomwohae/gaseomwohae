package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class WeatherResponseDto {
    private LocalDate date;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer sky;
}
