package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<ScheduleDTO> findAll();
    ScheduleDTO findById(Long id);
    List<ScheduleDTO> findByTravelId(Long travelId);
    void insert(ScheduleDTO schedule);
    void update(ScheduleDTO schedule);
    void delete(Long id);
    void softDelete(Long id);
} 