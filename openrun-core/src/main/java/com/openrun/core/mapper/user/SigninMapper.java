package com.openrun.core.mapper.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import com.openrun.core.domain.user.User;

@Mapper
public interface SigninMapper {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
