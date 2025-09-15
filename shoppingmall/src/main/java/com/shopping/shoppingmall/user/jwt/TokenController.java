package com.shopping.shoppingmall.user.jwt;


import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingmall.redis.JwtRedisService;
import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.signin.SigninMapper;

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

        Optional<User> user = signinMapper.findById(userId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, user.get().getEmail()); // 이메일은 DB 조회 추천
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
