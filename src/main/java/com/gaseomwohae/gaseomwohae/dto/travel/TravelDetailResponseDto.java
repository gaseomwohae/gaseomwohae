package com.gaseomwohae.gaseomwohae.dto.travel;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.Place;
import com.gaseomwohae.gaseomwohae.dto.Schedule;
import com.gaseomwohae.gaseomwohae.dto.Travel;
import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TravelDetailResponseDto {
	private Travel travel;
	private List<GetUserInfoResponseDto> participants;
	private List<Schedule> schedules;
	private List<Place> accommodations;
}
