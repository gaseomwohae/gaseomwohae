package com.gaseomwohae.gaseomwohae.dto.travel;

import java.util.List;
import java.util.Map;

import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;
import com.gaseomwohae.gaseomwohae.model.Place;
import com.gaseomwohae.gaseomwohae.model.Schedule;
import com.gaseomwohae.gaseomwohae.model.Supply;
import com.gaseomwohae.gaseomwohae.model.Travel;

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
	private Map<String, List<Supply>> supplies;
}
