package com.shopping.shoppingmall.user.mypage;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopping.shoppingmall.user.domain.UserDetailsImpl;
import com.shopping.shoppingmall.user.signin.SigninMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final SigninMapper signinMapper;

    // userDetails에 null이 할당되어서 에러발생함.
    // AuthenticationPrincipal  동작원리 알아보기
    // 로그인하고 새로고침하면 다시 로그인버튼나옴 -> 모델기반 데이터전달은 새로고침하면 사라짐 -> 세션 혹은 쿠키기반으로 하도록 변경

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());

        if (userDetails == null) {
            return "redirect:/"; // 로그인 안 돼 있으면 홈으로 리다이렉트
        }

        model.addAttribute("user", signinMapper.findByEmail(userDetails.getUsername()));
        return "mypage"; // mypage.html 렌더링
    }
}

