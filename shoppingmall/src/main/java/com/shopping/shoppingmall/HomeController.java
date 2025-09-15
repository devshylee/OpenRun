package com.shopping.shoppingmall;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopping.shoppingmall.user.signin.SigninRequest;

@Controller
public class HomeController {

   @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("signinRequest", new SigninRequest());
        return "index";
    }
    
}
