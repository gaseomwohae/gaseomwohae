package com.gaseomwohae.gaseomwohae.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class
Participant {
	private Long id;
	private Long userId;
	private Long travelId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
} 