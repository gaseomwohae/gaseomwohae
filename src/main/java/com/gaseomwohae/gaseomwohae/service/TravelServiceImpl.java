package com.gaseomwohae.gaseomwohae.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaseomwohae.gaseomwohae.common.exception.ErrorCode;
import com.gaseomwohae.gaseomwohae.common.exception.exceptions.BadRequestException;
import com.gaseomwohae.gaseomwohae.dto.region.LocationDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.ScheduleDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.schedule.ScheduleListResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.AddSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.CreateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.DeleteSupplyRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteListResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.InviteParticipantRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.TravelDetailResponseDto;
import com.gaseomwohae.gaseomwohae.dto.travel.UpdateTravelRequestDto;
import com.gaseomwohae.gaseomwohae.dto.travel.WeatherResponseDto;
import com.gaseomwohae.gaseomwohae.dto.user.GetUserInfoResponseDto;
import com.gaseomwohae.gaseomwohae.model.Invite;
import com.gaseomwohae.gaseomwohae.model.Participant;
import com.gaseomwohae.gaseomwohae.model.Place;
import com.gaseomwohae.gaseomwohae.model.Schedule;
import com.gaseomwohae.gaseomwohae.model.Supply;
import com.gaseomwohae.gaseomwohae.model.Travel;
import com.gaseomwohae.gaseomwohae.model.TravelSupply;
import com.gaseomwohae.gaseomwohae.model.User;
import com.gaseomwohae.gaseomwohae.repository.InviteRepository;
import com.gaseomwohae.gaseomwohae.repository.ParticipantRepository;
import com.gaseomwohae.gaseomwohae.repository.PlaceRepository;
import com.gaseomwohae.gaseomwohae.repository.RegionRepository;
import com.gaseomwohae.gaseomwohae.repository.ScheduleRepository;
import com.gaseomwohae.gaseomwohae.repository.SupplyRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelRepository;
import com.gaseomwohae.gaseomwohae.repository.TravelSupplyRepository;
import com.gaseomwohae.gaseomwohae.repository.UserRepository;
import com.gaseomwohae.gaseomwohae.util.ApiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
	private final TravelRepository travelRepository;
	private final ParticipantRepository participantRepository;
	private final ScheduleRepository scheduleRepository;
	private final UserRepository userRepository;
	private final InviteRepository inviteRepository;	
	private final PlaceRepository placeRepository;
	private final SupplyRepository supplyRepository;
	private final TravelSupplyRepository travelSupplyRepository;
	private final ApiService apiService;
	private final RegionService regionService;
	private final RegionRepository regionRepository;
	
	@Override
	public TravelDetailResponseDto getTravel(Long userId, Long travelId) {
		Travel travel = travelRepository.findById(travelId);
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<Participant> participantList = participantRepository.findByTravelId(travelId);
		if (!participantList.stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		// 참여자 목록
		List<GetUserInfoResponseDto> participants = new ArrayList<>();
		participantList.forEach((participant -> {
			User user = userRepository.findById(participant.getUserId());
			if (user != null) {
				participants.add(GetUserInfoResponseDto.builder()
					.id(user.getId())
					.name(user.getName())
					.email(user.getEmail())
					.profileImage(user.getProfileImage())
					.createdAt(user.getCreatedAt())
					.build());
			}
		}));

		// 일정 정보
		List<Schedule> scheduleList = scheduleRepository.findByTravelId(travelId);

		// 숙소 정보
		List<Place> accommodations = new ArrayList<>();
		scheduleList.forEach((schedule) -> {
			Place place = placeRepository.findById(schedule.getPlaceId());
			if (place != null && place.getCategory().contains("숙박")) {
				accommodations.add(place);
			}
		});

		// 여행 물품 목록
		List<TravelSupply> travelSupplies = travelSupplyRepository.findAllByTravelId(travelId);
		List<Supply> supplies = new ArrayList<>();
		travelSupplies.forEach((travelSupply) -> {
			supplies.add(supplyRepository.findById(travelSupply.getSupplyId()));
		});

		Map<String, List<Supply>> suppliesByCategory = new HashMap<>();
		supplies.forEach((supply) -> {
			suppliesByCategory
				.computeIfAbsent(supply.getCategory(), k -> new ArrayList<>())
				.add(supply);
		});

		// 목적지의 날씨정보
		String destination = travel.getDestination();
		LocationDto location = regionRepository.getLocation(destination.split(" ")[0], destination.split(" ")[1]);

		List<WeatherResponseDto> weatherInfo = apiService.getWeatherInfo(location.getY().doubleValue(), location.getX().doubleValue());


		// 응답 데이터 생성
		return TravelDetailResponseDto.builder()
			.travel(travel)
			.participants(participants)
			.schedules(scheduleList.stream()
			.collect(Collectors.groupingBy(Schedule::getDate))
			.entrySet().stream()
			.map(entry -> ScheduleListResponseDto.builder()
				.date(entry.getKey())
				.schedule(entry.getValue().stream()
					.map(schedule -> ScheduleDetailResponseDto.builder()
						.scheduleId(schedule.getId())
						.date(schedule.getDate())
						.startTime(schedule.getStartTime())
						.endTime(schedule.getEndTime())
						.build())
					.toArray(ScheduleDetailResponseDto[]::new))
				.build())
			.collect(Collectors.toList()))
			.accommodations(accommodations)
			.supplies(suppliesByCategory)
			.weatherInfos(weatherInfo)
			.build();
	}

	@Override
	public List<Travel> getTravelList(Long userId) {
		List<Participant> participantList = participantRepository.findByUserId(userId);

		List<Travel> travelList = new ArrayList<>();
		participantList.forEach((participant -> {
			Travel travel = travelRepository.findById(participant.getTravelId());
			if (travel != null) {
				travelList.add(travel);
			}
		}));
		return travelList;
	}

	@Override
	@Transactional
	public void createTravel(Long userId, CreateTravelRequestDto createTravelRequestDto) {


		Travel newTravel = Travel.builder()
			.name(createTravelRequestDto.getName())
			.destination(createTravelRequestDto.getDestination())
			.startDate(createTravelRequestDto.getStartDate())
			.endDate(createTravelRequestDto.getEndDate())
			.budget(createTravelRequestDto.getBudget())
			.build();

		travelRepository.insert(newTravel);

		Participant participant = Participant.builder()
			.travelId(newTravel.getId())
			.userId(userId).
			build();

		participantRepository.insert(
			participant
		);
	}

	@Override
	public void updateTravel(Long travelId, UpdateTravelRequestDto updateTravelRequestDto) {

		Travel travel = travelRepository.findById(travelId);

		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Travel updateTravel = Travel.builder().
			id(travel.getId())
			.name(updateTravelRequestDto.getName() == null ? travel.getName() : updateTravelRequestDto.getName())
			.destination(updateTravelRequestDto.getDestination() == null ? travel.getDestination() :
				updateTravelRequestDto.getDestination())
			.startDate(updateTravelRequestDto.getStartDate() == null ? travel.getStartDate() :
				updateTravelRequestDto.getStartDate())
			.endDate(updateTravelRequestDto.getEndDate() == null ? travel.getEndDate() :
				updateTravelRequestDto.getEndDate())
			.budget(updateTravelRequestDto.getBudget() == null ? travel.getBudget() :
				updateTravelRequestDto.getBudget())
			.build();

		travelRepository.update(updateTravel);
	}

	@Override
	public void deleteTravel(Long userId, Long travelId) {
		Travel travel = travelRepository.findById(travelId);
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<Participant> participantList = participantRepository.findByTravelId(travelId);
		if (!participantList.stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		travelRepository.delete(travelId);
	}

	@Override
	public void inviteParticipant(Long userId, InviteParticipantRequestDto inviteParticipantRequestDto) {
		
		User invitedUser = userRepository.findByEmail(inviteParticipantRequestDto.getEmail());
		if (invitedUser == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Travel travel = travelRepository.findById(inviteParticipantRequestDto.getTravelId());
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 내가 참여중인 여행인지 확인
		Participant participant = participantRepository.findByTravelIdAndUserId(inviteParticipantRequestDto.getTravelId(), userId);
		if (participant == null) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		// 초대할 사람이 이미 여행에 참여중인지 확인
		Participant inviteParticipant = participantRepository.findByTravelIdAndUserId(inviteParticipantRequestDto.getTravelId(), invitedUser.getId());
		if (inviteParticipant != null) {
			throw new BadRequestException(ErrorCode.RESOURCE_ALREADY_EXISTS);
		}

		// 초대하기
		inviteRepository.insert(Invite.builder()
			.inviterUserId(userId)
			.invitedUserId(invitedUser.getId())
			.travelId(inviteParticipantRequestDto.getTravelId())
			.build()
		);
	}

	@Override
	public List<InviteListResponseDto> getInviteList(Long userId) {
		List<Invite> inviteList = inviteRepository.findManyByInvitedUserId(userId);

		List<InviteListResponseDto> inviteListResponseDtoList = new ArrayList<>();
		inviteList.forEach((invite) -> {
			Travel travel = travelRepository.findById(invite.getTravelId());
			inviteListResponseDtoList.add(InviteListResponseDto.builder()
				.inviteId(invite.getId())
				.travel(travel)
				.inviterUser(GetUserInfoResponseDto.builder()
					.id(invite.getInviterUserId())
					.name(userRepository.findById(invite.getInviterUserId()).getName())
					.email(userRepository.findById(invite.getInviterUserId()).getEmail())
					.profileImage(userRepository.findById(invite.getInviterUserId()).getProfileImage())
					.createdAt(userRepository.findById(invite.getInviterUserId()).getCreatedAt())
					.build())
				.invitedUser(GetUserInfoResponseDto.builder()
					.id(invite.getInvitedUserId())
					.name(userRepository.findById(invite.getInvitedUserId()).getName())
					.email(userRepository.findById(invite.getInvitedUserId()).getEmail())
					.profileImage(userRepository.findById(invite.getInvitedUserId()).getProfileImage())
					.createdAt(userRepository.findById(invite.getInvitedUserId()).getCreatedAt())
					.build())
				.createdAt(invite.getCreatedAt())
				.build());
		});

		return inviteListResponseDtoList;
	}

	@Override
	public void acceptInvite(Long userId, Long inviteId) {
		Invite invite = inviteRepository.findById(inviteId);
		if (invite == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!invite.getInvitedUserId().equals(userId)) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		Participant newParticipant = Participant.builder()
			.travelId(invite.getTravelId())
			.userId(invite.getInvitedUserId())
			.build();

		participantRepository.insert(newParticipant);


		// 초대 삭제
		inviteRepository.delete(inviteId);
	}

	@Override
	public void rejectInvite(Long userId, Long inviteId) {
		Invite invite = inviteRepository.findById(inviteId);
		if (invite == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!invite.getInvitedUserId().equals(userId)) {
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);
		}

		// 초대 삭제
		inviteRepository.delete(inviteId);
	}

	@Override
	public Map<String, List<Supply>> getSupplyList(Long userId) {
		List<Supply> supplies = supplyRepository.findAll();
		Map<String, List<Supply>> suppliesByCategory = new HashMap<>();

		for (Supply supply : supplies) {
			String category = supply.getCategory(); // Assuming Supply has a getCategory() method
			suppliesByCategory
				.computeIfAbsent(category, k -> new ArrayList<>())
				.add(supply);
		};

		return suppliesByCategory;
	}

	@Override
	public void addSupply(Long userId, AddSupplyRequestDto addSupplyRequestDto) {
		Supply addSupply = supplyRepository.findById(addSupplyRequestDto.getSupplyId());
		if (addSupply == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Travel travel = travelRepository.findById(addSupplyRequestDto.getTravelId());
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		TravelSupply newTravelSupply = TravelSupply.builder()
			.travelId(addSupplyRequestDto.getTravelId())
			.supplyId(addSupplyRequestDto.getSupplyId())
			.build();

		travelSupplyRepository.insert(newTravelSupply);
	}

	@Override
	public void deleteSupply(Long userId, DeleteSupplyRequestDto deleteSupplyRequestDto) {
		Supply supply = supplyRepository.findById(deleteSupplyRequestDto.getSupplyId());
		if (supply == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Travel travel = travelRepository.findById(deleteSupplyRequestDto.getTravelId());
		if (travel == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		TravelSupply travelSupply = travelSupplyRepository.findByTravelIdAndSupplyId(deleteSupplyRequestDto.getTravelId(), deleteSupplyRequestDto.getSupplyId());
		if (travelSupply == null) {
			throw new BadRequestException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		travelSupplyRepository.delete(travelSupply.getId());
	}
}
