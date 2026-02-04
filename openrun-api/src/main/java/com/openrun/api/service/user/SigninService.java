package com.openrun.api.service.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.openrun.core.infra.redis.JwtRedisService;
import com.openrun.core.domain.user.User;
import com.openrun.core.infra.jwt.JwtTokenProvider;
import com.openrun.core.mapper.user.SigninMapper;
import com.openrun.api.dto.user.SigninRequest;
import com.openrun.api.dto.user.SigninResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigninService {

    private final SigninMapper signinMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRedisService redisService;

    public SigninResponse login(SigninRequest request) {

        Optional<User> user = signinMapper.findByEmail(request.getEmail());

        // 보안상 아이디/비밀번호 오류를 구분하지 않음
        if (!user.isPresent()) {
            throw new org.springframework.security.authentication.BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        log.debug("비밀번호 검증 시작: email={}", request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new org.springframework.security.authentication.BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.get().getId(), user.get().getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.get().getId());

        redisService.saveRefreshToken(user.get().getId(), refreshToken);

        return new SigninResponse(accessToken, refreshToken);
    }

}
