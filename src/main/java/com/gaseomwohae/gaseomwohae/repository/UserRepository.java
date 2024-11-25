package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.model.User;

@Mapper
public interface UserRepository {
	List<User> findAll();

	User findById(Long id);

	User findByEmail(String email);

	void insert(User user);

	void update(User user);

	void delete(Long id);

	void softDelete(Long id);
} 