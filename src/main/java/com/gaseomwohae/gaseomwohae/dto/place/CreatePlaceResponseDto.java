package com.gaseomwohae.gaseomwohae.dto.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatePlaceResponseDto {
    private Long id;
    private Double rating;
}
