package com.shopping.shoppingmall.user.signup;

import org.apache.ibatis.annotations.Mapper;

import com.shopping.shoppingmall.user.domain.User;

@Mapper
public interface SignupMapper {
    void insertUser(User user);
}
