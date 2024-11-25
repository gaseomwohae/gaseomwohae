package com.gaseomwohae.gaseomwohae.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Place {
	private Long id;
	private String name;
	private String category;
	private String address;
	private String roadAddress;
	private String thumbnail;
	private String phone;
	private String url;
	private BigDecimal x;
	private BigDecimal y;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
} 