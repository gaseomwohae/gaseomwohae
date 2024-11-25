package com.gaseomwohae.gaseomwohae.dto;

import java.sql.Timestamp;

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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
