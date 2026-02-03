package com.openrun.core.mapper.user;

import org.apache.ibatis.annotations.Mapper;

import com.openrun.core.domain.user.User;

@Mapper
public interface SignupMapper {
    void insertUser(User user);

    int findUserById(String userId);
}
