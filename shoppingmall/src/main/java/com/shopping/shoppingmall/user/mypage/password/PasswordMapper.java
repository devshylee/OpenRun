package com.shopping.shoppingmall.user.mypage.password;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PasswordMapper {
    
    String findPasswordByUserId(Long userId);
    int updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
    
}
