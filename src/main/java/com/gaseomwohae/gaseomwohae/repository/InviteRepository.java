package com.gaseomwohae.gaseomwohae.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gaseomwohae.gaseomwohae.dto.Invite;

@Mapper
public interface InviteRepository {

    Invite findById(Long id);

    List<Invite> findManyByInvitedUserId(Long invitedUserId);

    void insert(Invite invite);

    void delete(Long id);
} 