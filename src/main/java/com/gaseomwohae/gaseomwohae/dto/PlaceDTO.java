package com.gaseomwohae.gaseomwohae.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PlaceDTO {
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