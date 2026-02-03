package com.openrun.api.controller.auth;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.core.infra.redis.JwtRedisService;
import com.openrun.core.domain.user.User;
import com.openrun.core.mapper.user.SigninMapper;
import com.openrun.core.infra.jwt.JwtTokenProvider;
import com.openrun.api.dto.auth.TokenRequest;
import com.openrun.api.dto.auth.TokenResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRedisService refreshTokenService;
    private final SigninMapper signinMapper;

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody TokenRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(request.getRefreshToken());
        String storedToken = refreshTokenService.getRefreshToken(userId);

        if (!request.getRefreshToken().equals(storedToken)) {
            throw new IllegalArgumentException("이미 로그아웃된 토큰입니다.");
        }

        User user = signinMapper.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));
        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, user.getEmail());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
