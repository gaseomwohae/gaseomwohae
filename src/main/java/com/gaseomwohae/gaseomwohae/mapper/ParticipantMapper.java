package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.ParticipantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParticipantMapper {
    List<ParticipantDTO> findAll();
    ParticipantDTO findById(Long id);
    List<ParticipantDTO> findByTravelId(Long travelId);
    List<ParticipantDTO> findByUserId(Long userId);
    void insert(ParticipantDTO participant);
    void update(ParticipantDTO participant);
    void delete(Long id);
    void softDelete(Long id);
} 