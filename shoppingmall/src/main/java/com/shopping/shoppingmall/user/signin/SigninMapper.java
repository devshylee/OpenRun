package com.shopping.shoppingmall.user.signin;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import com.shopping.shoppingmall.user.domain.User;

@Mapper
public interface SigninMapper {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
