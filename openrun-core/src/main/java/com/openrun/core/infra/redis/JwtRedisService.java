package com.openrun.core.infra.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    public void saveRefreshToken(Long userId, String refreshToken) {
        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + userId,
                refreshToken,
                Duration.ofDays(30));
    }

    public String getRefreshToken(Long userId) {
        Object result = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
        if (result instanceof String) {
            return (String) result;
        }
        return null;
    }

    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

}
