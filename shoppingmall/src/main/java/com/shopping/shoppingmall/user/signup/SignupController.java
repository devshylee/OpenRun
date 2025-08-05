package com.shopping.shoppingmall.user.signup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// 1. 컨트롤러에 넘어온 request 유효성 검사 - 완
// 2. Mapper.xml 구현 - 완
// 3. Service 구현 *필터링포함

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupForm", new SignupRequest());
        return "user/signup";
    }

    // 커스텀 예외 만들고 전역 예외처리 클래스에서 처리하도록 나중에 변경 
     @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("signupForm") SignupRequest signupRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // "redirect"가 아닌 "user/signup" 뷰를 바로 반환하여 같은 화면에 에러메시지 표시
            return "user/signup";
        }
        try {
            signupService.signup(signupRequest);
       } catch (IllegalStateException e) {
            // 서비스에서 아이디 중복 등의 예외 발생 시 처리
            bindingResult.reject("signupFail", e.getMessage());
            return "user/signup";
        }
        return "redirect:/login";
    }
    
}