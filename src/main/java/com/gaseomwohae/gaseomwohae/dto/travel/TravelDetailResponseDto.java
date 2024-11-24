package com.gaseomwohae.gaseomwohae.dto.travel;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.Participant;
import com.gaseomwohae.gaseomwohae.dto.Schedule;
import com.gaseomwohae.gaseomwohae.dto.Travel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TravelDetailResponseDto {
	private Travel travel;
	private List<Participant> participants;
	private List<Schedule> schedules;
}
