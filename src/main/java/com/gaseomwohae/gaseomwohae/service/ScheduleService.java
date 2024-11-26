package com.gaseomwohae.gaseomwohae.service;

import java.util.List;

import com.gaseomwohae.gaseomwohae.dto.schedule.CreateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.ScheduleListResponseDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.UpdateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.model.Schedule;

public interface ScheduleService {
	Schedule createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto);

	List<ScheduleListResponseDto> getSchedules(Long userId, Long travelId);

	void updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);

	void deleteSchedule(Long userId, Long scheduleId);
}
