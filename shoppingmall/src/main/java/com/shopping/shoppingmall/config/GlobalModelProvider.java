package com.shopping.shoppingmall.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.shopping.shoppingmall.user.signin.SigninRequest; // 경로는 실제 패키지에 맞게 조정

@ControllerAdvice
public class GlobalModelProvider {

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("signinRequest")
    public SigninRequest signinRequest() {
        return new SigninRequest(); // 기본 빈 객체 제공 (모달 폼 바인딩용)
    }
}
