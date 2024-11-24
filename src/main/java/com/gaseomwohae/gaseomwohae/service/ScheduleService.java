package com.gaseomwohae.gaseomwohae.service;

import com.gaseomwohae.gaseomwohae.dto.schedule.CreateScheduleRequestDto;

public interface ScheduleService {
    void createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto);
}
