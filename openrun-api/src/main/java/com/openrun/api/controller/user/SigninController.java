package com.openrun.api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.api.dto.user.SigninRequest;
import com.openrun.api.dto.user.SigninResponse;
import com.openrun.api.service.user.SigninService;
import com.openrun.common.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Signin", description = "로그인 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SigninController {

    private final SigninService signinService;

    @Operation(summary = "회원로그인", description = "이메일/비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SigninResponse>> login(
            @Valid @RequestBody SigninRequest request,
            HttpServletResponse response) {

        SigninResponse signinResponse = signinService.login(request);

        if (signinResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("아이디 또는 비밀번호가 잘못되었습니다."));
        }

        // AccessToken을 HttpOnly 쿠키에 담아 전달
        Cookie accessTokenCookie = new Cookie("ACCESS_TOKEN", signinResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1시간
        response.addCookie(accessTokenCookie);

        // RefreshToken을 HttpOnly 쿠키에 담아 전달
        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", signinResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(ApiResponse.success(signinResponse, "로그인 성공"));
    }
}
