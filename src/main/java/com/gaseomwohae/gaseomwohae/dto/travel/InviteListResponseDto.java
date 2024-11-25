package com.gaseomwohae.gaseomwohae.dto.travel;

import java.time.LocalDateTime;

import com.gaseomwohae.gaseomwohae.model.Travel;
import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InviteListResponseDto {
    private Long inviteId;
    private Travel travel;
	private GetUserInfoResponseDto inviterUser;
	private GetUserInfoResponseDto invitedUser;
	private LocalDateTime createdAt;
}