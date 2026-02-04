package com.openrun.api.controller.mail;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.core.infra.mail.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public void sendCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        mailService.sendEmail(email);
    }

    @PostMapping("/verify")
    public void verifyCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String code = requestBody.get("code");

        mailService.verifyCode(email, code);
    }

}
