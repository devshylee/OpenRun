package com.shopping.shoppingmall.user.signout;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Signout", description = "로그아웃 API")
@Controller
@RequiredArgsConstructor
public class SignoutController {

    private final RedisTemplate<String, String> redisTemplate;
	
    @Operation(summary = "로그아웃", description = "JWT토큰을 블랙리스트에 등록하여 로그아웃을 진행")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (필수값 누락 또는 형식 오류)"),
        @ApiResponse(responseCode = "422", description = "입력값 유효성 검증 실패")
    })
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        System.out.println("로그아웃 실행");
        // 1. 쿠키에서 Refresh Token 추출
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        System.out.println("refreshToken : " + refreshToken);

        // 2. Redis에서 Refresh Token 삭제
        if (refreshToken != null) {
            redisTemplate.delete(refreshToken);
        }

        // 3. 쿠키 만료
        Cookie expiredCookie = new Cookie("refreshToken", null);
        expiredCookie.setMaxAge(0);
        expiredCookie.setPath("/");
        expiredCookie.setHttpOnly(true);
        expiredCookie.setSecure(true);
        response.addCookie(expiredCookie);

        // 4. SecurityContext 초기화
        SecurityContextHolder.clearContext();

        redirectAttributes.addFlashAttribute("isLoggedIn", false);

        return "redirect:/";
    }
}
