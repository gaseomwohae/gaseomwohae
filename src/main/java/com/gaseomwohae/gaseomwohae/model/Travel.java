package com.gaseomwohae.gaseomwohae.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Travel {
	private Long id;
	private String name;
	private String destination;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer budget;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
} 