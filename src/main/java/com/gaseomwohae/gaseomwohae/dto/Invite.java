package com.gaseomwohae.gaseomwohae.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Invite {
    private Long id;
    private Long inviterUserId;
    private Long invitedUserId;
    private Long travelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
