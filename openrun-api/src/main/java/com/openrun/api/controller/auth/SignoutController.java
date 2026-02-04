package com.openrun.api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.common.dto.ApiResponse;
import com.openrun.core.infra.jwt.JwtTokenProvider;
import com.openrun.core.infra.redis.JwtRedisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Signout", description = "로그아웃 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignoutController {

    private final JwtRedisService jwtRedisService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그아웃", description = "JWT토큰을 블랙리스트에 등록하여 로그아웃을 진행")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("REFRESH_TOKEN".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        // Redis에서 Refresh Token 삭제
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            try {
                Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
                jwtRedisService.deleteRefreshToken(userId);
            } catch (Exception e) {
                log.warn("로그아웃 중 토큰 처리 오류: {}", e.getMessage());
            }
        }

        // 쿠키 만료
        Cookie expiredAccess = new Cookie("ACCESS_TOKEN", null);
        expiredAccess.setMaxAge(0);
        expiredAccess.setPath("/");
        expiredAccess.setHttpOnly(true);
        expiredAccess.setSecure(true);
        response.addCookie(expiredAccess);

        Cookie expiredRefresh = new Cookie("REFRESH_TOKEN", null);
        expiredRefresh.setMaxAge(0);
        expiredRefresh.setPath("/");
        expiredRefresh.setHttpOnly(true);
        expiredRefresh.setSecure(true);
        response.addCookie(expiredRefresh);

        // SecurityContext 초기화
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(ApiResponse.success(null, "로그아웃 성공"));
    }
}
