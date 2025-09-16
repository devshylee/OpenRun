package com.shopping.shoppingmall.user.signout;

import org.springframework.stereotype.Service;

import com.shopping.shoppingmall.redis.JwtRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignoutService {

    private final JwtRedisService redisService;

    public void logoutAllDevices(Long userId) {
		redisService.deleteRefreshToken(userId);
	}
}
