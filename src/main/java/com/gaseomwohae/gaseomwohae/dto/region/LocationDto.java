package com.gaseomwohae.gaseomwohae.dto.region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class LocationDto {
    private BigDecimal y; // 위도
    private BigDecimal x; // 경도
}
