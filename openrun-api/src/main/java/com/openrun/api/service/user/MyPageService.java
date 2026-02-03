package com.openrun.api.service.user;

import org.springframework.stereotype.Service;

import com.openrun.core.domain.user.User;
import com.openrun.core.domain.user.UserDetailsImpl;
import com.openrun.core.mapper.user.SigninMapper;
import com.openrun.core.mapper.user.MyPageMapper;
import com.openrun.core.dto.MyPageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final SigninMapper signinMapper;
    private final MyPageMapper myPageMapper;

    public User findUser(UserDetailsImpl userDetails) {
        return signinMapper.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new com.openrun.common.exception.UserNotFoundException());
    }

    public void update(MyPageDTO myPageDTO, UserDetailsImpl userDetails) {

        log.debug("사용자 조회 완료: email={}", userDetails.getUsername());

        User user = signinMapper.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new com.openrun.common.exception.UserNotFoundException());

        log.debug("사용자 정보 업데이트: {}", user);
        myPageMapper.updateMember(myPageDTO, user.getEmail());
    }

}
