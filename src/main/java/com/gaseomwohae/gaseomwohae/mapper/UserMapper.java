package com.gaseomwohae.gaseomwohae.mapper;

import com.gaseomwohae.gaseomwohae.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO findByEmail(String email);
    void insert(UserDTO user);
    void update(UserDTO user);
    void delete(Long id);
    void softDelete(Long id);
} 