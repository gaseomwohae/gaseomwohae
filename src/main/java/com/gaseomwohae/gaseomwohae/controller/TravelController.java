package com.gaseomwohae.gaseomwohae.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaseomwohae.gaseomwohae.dto.travel.AddSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.DeleteSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteListResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteParticipantRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.TravelDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.UpdateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.model.Supply;
import com.gaseomwohae.gaseomwohae.model.Travel;
import com.gaseomwohae.gaseomwohae.service.TravelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {

	private final TravelService travelService;

	@GetMapping("/{travel-id}")
	public TravelDetailResponseDto getTravel(@AuthenticationPrincipal Long userId, @PathVariable("travel-id") Long travelId) {
		return travelService.getTravel(userId, travelId);
	}

	@GetMapping
	public List<Travel> getTravelList(@AuthenticationPrincipal Long userId) {
		return travelService.getTravelList(userId);
	}

	@PostMapping
	public void createTravel(@AuthenticationPrincipal Long userId,
		@RequestBody @Valid CreateTravelRequestDto createTravelRequestDto) {
		travelService.createTravel(userId, createTravelRequestDto);
	}

	@PutMapping("/{travel-id}")
	public void updateTravel(@PathVariable("travel-id") Long travelId, @RequestBody UpdateTravelRequestDto updateTravelRequestDto) {
		travelService.updateTravel(travelId, updateTravelRequestDto);
	}

	@DeleteMapping("/{travel-id}")
	public void deleteTravel(@AuthenticationPrincipal Long userId, @PathVariable("travel-id") Long travelId) {
		travelService.deleteTravel(userId, travelId);
	}

	@PostMapping("/invite")
	public void inviteParticipant(@AuthenticationPrincipal Long userId, @RequestBody InviteParticipantRequestDto inviteParticipantRequestDto) {
		travelService.inviteParticipant(userId, inviteParticipantRequestDto);
	}

	@GetMapping("/invite")
	public List<InviteListResponseDto> getInviteList(@AuthenticationPrincipal Long userId) {
		return travelService.getInviteList(userId);
	}

	@PostMapping("/invite/accept/{invite-id}")
	public void acceptInvite(@AuthenticationPrincipal Long userId, @PathVariable("invite-id") Long inviteId) {
		travelService.acceptInvite(userId, inviteId);
	}

	@PostMapping("/invite/reject/{invite-id}")
	public void rejectInvite(@AuthenticationPrincipal Long userId, @PathVariable("invite-id") Long inviteId) {
		travelService.rejectInvite(userId, inviteId);
	}

	@GetMapping("/supply")
	public Map<String, List<Supply>> getSupplyList(@AuthenticationPrincipal Long userId) {
		return travelService.getSupplyList(userId);
	}
	
	@PostMapping("/supply")
	public void addSupply(@AuthenticationPrincipal Long userId, @RequestBody AddSupplyRequestDto addSupplyRequestDto) {
		travelService.addSupply(userId, addSupplyRequestDto);
	}

	@DeleteMapping("/supply")
	public void deleteSupply(@AuthenticationPrincipal Long userId, @RequestBody DeleteSupplyRequestDto deleteSupplyRequestDto) {
		travelService.deleteSupply(userId, deleteSupplyRequestDto);
	}
}
