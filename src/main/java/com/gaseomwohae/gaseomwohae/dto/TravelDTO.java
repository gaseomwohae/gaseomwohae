package com.gaseomwohae.gaseomwohae.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TravelDTO {
    private Long id;
    private String name;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
} 