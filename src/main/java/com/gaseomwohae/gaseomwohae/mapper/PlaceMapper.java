package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.PlaceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceMapper {
    List<PlaceDTO> findAll();
    PlaceDTO findById(Long id);
    List<PlaceDTO> findByCategory(String category);
    void insert(PlaceDTO place);
    void update(PlaceDTO place);
    void delete(Long id);
    void softDelete(Long id);
} 