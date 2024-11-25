package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.schedule.CreateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.UpdateScheduleRequestDto;
import com.gaseomwohae.gaseomwohae.model.Schedule;
import com.gaseomwohae.gaseomwohae.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
	private final ScheduleService scheduleService;

	@GetMapping
	public List<Schedule> getSchedules(@AuthenticationPrincipal Long userId, @RequestParam("travel-id") Long travelId) {
		return scheduleService.getSchedules(userId, travelId);
	}

	@PostMapping
	public Schedule createSchedule(@AuthenticationPrincipal Long userId,
		@RequestBody @Valid CreateScheduleRequestDto createScheduleRequestDto) {
		return scheduleService.createSchedule(userId, createScheduleRequestDto);
	}

	@PutMapping("/{schedule-id}")
	public void updateSchedule(@AuthenticationPrincipal Long userId, @PathVariable("schedule-id") Long scheduleId,
		@RequestBody @Valid UpdateScheduleRequestDto updateScheduleRequestDto) {
		scheduleService.updateSchedule(userId, scheduleId, updateScheduleRequestDto);
	}

	@DeleteMapping("/{schedule-id}")
	public void deleteSchedule(@AuthenticationPrincipal Long userId, @PathVariable("schedule-id") Long scheduleId) {
		scheduleService.deleteSchedule(userId, scheduleId);
	}

}
