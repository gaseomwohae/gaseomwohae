package com.gaseomwohae.gaseomwohae.service;

import com.gaseomwohae.gaseomwohae.dto.schedule.CreateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.UpdateScheduleRequestDto;

public interface ScheduleService {
    void createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto);
    void updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);
    void deleteSchedule(Long userId, Long scheduleId);
}
