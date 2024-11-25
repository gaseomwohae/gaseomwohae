package com.gaseomwohae.gaseomwohae.service;

import java.util.List;
import java.util.Map;

import com.gaseomwohae.gaseomwohae.dto.travel.AddSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.DeleteSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteListResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteParticipantRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.TravelDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.UpdateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.model.Supply;
import com.gaseomwohae.gaseomwohae.model.Travel;

public interface TravelService {
	public TravelDetailResponseDto getTravel(Long userId, Long travelId);

	public List<Travel> getTravelList(Long userId);

	public void createTravel(Long userId, CreateTravelRequestDto createTravelRequestDto);

	public void updateTravel(Long travelId, UpdateTravelRequestDto updateTravelRequestDto);

	public void deleteTravel(Long userId, Long travelId);

	public void inviteParticipant(Long userId, InviteParticipantRequestDto inviteParticipantRequestDto);

	public List<InviteListResponseDto> getInviteList(Long userId);

	public void acceptInvite(Long userId, Long inviteId);

	public void rejectInvite(Long userId, Long inviteId);

	public Map<String, List<Supply>> getSupplyList(Long userId);

	public void addSupply(Long userId, AddSupplyRequestDto addSupplyRequestDto);

	public void deleteSupply(Long userId, DeleteSupplyRequestDto deleteSupplyRequestDto);
}
