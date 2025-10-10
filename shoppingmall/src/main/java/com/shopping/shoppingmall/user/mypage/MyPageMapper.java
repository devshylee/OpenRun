package com.shopping.shoppingmall.user.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPageMapper {
    void updateMember(@Param("dto") MyPageDTO myPageDTO, @Param("email") String email);
}
