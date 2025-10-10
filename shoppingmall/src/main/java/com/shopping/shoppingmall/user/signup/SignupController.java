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
        model.addAttribute("signupForm", new SignupDTO());
        return "user/signup";
    }

    // 커스텀 예외 만들고 전역 예외처리 클래스에서 처리하도록 나중에 변경 
    @PostMapping("/signup")
    public String processSignup(
            @Valid @ModelAttribute("signupForm") SignupDTO signupRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        signupService.signup(signupRequest);
        return "redirect:/index";
    }

}