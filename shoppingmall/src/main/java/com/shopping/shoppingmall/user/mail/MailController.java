package com.shopping.shoppingmall.user.mail;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public void sendCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        mailService.sendEmail(email);
    }

    
    @PostMapping("/verify")
    public void verifyCode(String code) {
    
    }
    

}
