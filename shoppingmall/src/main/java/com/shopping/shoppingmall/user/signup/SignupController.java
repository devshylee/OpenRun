package com.shopping.shoppingmall.user.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// 1. 컨트롤러에 넘어온 request 유효성 검사 - 완
// 2. Mapper.xml 구현 - 완
// 3. Service 구현 *필터링포함

@Controller
public class SignupController {

    @Autowired
    private SignupService signupService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupForm", new SignupRequest());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute SignupRequest request) {
        signupService.signup(request);
        return "redirect:/login";
    }
    
}