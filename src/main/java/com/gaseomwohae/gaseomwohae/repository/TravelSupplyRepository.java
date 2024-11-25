package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.TravelSupply;

@Mapper
public interface TravelSupplyRepository {

    List<TravelSupply> findManyById(Long travelSupplyId);

    TravelSupply findByTravelIdAndSupplyId(Long travelId, Long supplyId);

    List<TravelSupply> findAllByTravelId(Long travelId);

    void insert(TravelSupply travelSupply);

    void delete(Long travelSupplyId);
    
}
