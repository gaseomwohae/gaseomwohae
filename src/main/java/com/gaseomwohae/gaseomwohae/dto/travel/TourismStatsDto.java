package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TourismStatsDto {
    private LocalDate date;
    private int visitorCount;
}
