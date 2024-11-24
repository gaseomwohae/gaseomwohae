package com.gaseomwohae.gaseomwohae.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
} 