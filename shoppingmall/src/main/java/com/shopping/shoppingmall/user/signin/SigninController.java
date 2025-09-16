package com.shopping.shoppingmall.user.signin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Tag(name="Signin", description = "로그인 API")
@Controller
@RequiredArgsConstructor
public class SigninController {
	
    private final SigninService signinService;
    
    @Operation(summary = "회원로그인", description = "이메일/비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/signin")
    public String login(@ModelAttribute("signinRequest") SigninRequest request, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) {
       
            // 1. 서비스에서 로그인 로직을 처리하고 SigninResponse 객체를 받습니다.
            SigninResponse signinResponse = signinService.login(request); // 여기서 오류 로그인로직에 문제있는듯
            
            if(signinResponse == null){
                model.addAttribute("signinRequest", request);
                model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
                System.out.println("로그인실패");
                return "user/signin";
            }

            System.out.println("로그인 로직 실행됨");

            // 2. accessToken을 HttpOnly 쿠키에 담아 전달합니다.
            Cookie accessTokenCookie = new Cookie("ACCESS_TOKEN", signinResponse.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 60); // 1시간 (액세스 토큰 유효기간에 맞춤)
            response.addCookie(accessTokenCookie);

            System.out.println("액세스토큰 전달됨");

            // 3. refreshToken을 HttpOnly 쿠키에 담아 전달합니다.
            Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", signinResponse.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 (리프레시 토큰 유효기간에 맞춤)
            response.addCookie(refreshTokenCookie);
            
            System.out.println("리프레시토큰 전달됨");

            System.out.println("로그인성공");

            // 4. 로그인 성공 시 메인 페이지로 리다이렉트합니다.

            return "redirect:/";

    }
}
