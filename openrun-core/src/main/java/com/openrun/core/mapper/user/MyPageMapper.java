package com.openrun.core.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.openrun.core.dto.MyPageDTO;

@Mapper
public interface MyPageMapper {
    void updateMember(@Param("dto") MyPageDTO myPageDTO, @Param("email") String email);
}
