package com.shopping.shoppingmall.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationRateLimiter {

    // 클라우드 환경에서는 X-Forwarded-For로 진짜 IP를 가져와야 하고, Cloudflare나 AWS ALB 사용 시 해당 헤더 처리 로직을 조금 더 보강해야함
    // 왜냐? 클라우드 환경에서는 로드밸런서나 CDN과같은 프록시서버가 존재하기 때문에 request.getRemoteAddr(); 으로 IP를 가져오면 클라이언트의 IP가 아닌 프록시서버의 IP를 가져오게 됨.
    // 클라우드 환경 도입 시 변경 예정

    private final RedisTemplate<String, String> redisTemplate;

    private static final int MAX_REQUESTS = 5; // 최대 요청 횟수
    private static final long WINDOW_SECONDS = 1800; // 30분 제한

    public void checkRateLimit(String clientIp) {
        String key = "email_verification:" + clientIp;

        Long requestCount = redisTemplate.opsForValue().increment(key);

        if (requestCount == 1) {
            // 첫 요청이면 TTL 설정
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
        }

        if (requestCount != null && requestCount > MAX_REQUESTS) {
            throw new IllegalStateException("요청 한도를 초과했습니다. 잠시 후 다시 시도해주세요.");
        }
    }


}
