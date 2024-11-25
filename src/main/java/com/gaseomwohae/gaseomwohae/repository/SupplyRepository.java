package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.Supply;

@Mapper
public interface SupplyRepository {
    List<Supply> findAll();
    Supply findById(Long id);
}
