package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.TravelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelMapper {
    List<TravelDTO> findAll();
    TravelDTO findById(Long id);
    void insert(TravelDTO travel);
    void update(TravelDTO travel);
    void delete(Long id);
    void softDelete(Long id);
} 