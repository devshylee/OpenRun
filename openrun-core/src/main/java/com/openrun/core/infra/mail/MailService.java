package com.openrun.core.infra.mail;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // 추후 Redis로 대체
    private final Map<String, String> codeStorage = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet();

    public void sendEmail(String to) {

        String code = generateCode(to);

        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(to);
        // message.setSubject("이메일 인증코드입니다. [테스트]");
        // message.setText("인증코드: " + code + "\n\n5분 이내에 입력해주세요.");
        // mailSender.send(message);

        System.out.println(code);

    }

    public String generateCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        codeStorage.put(email, code);
        return code;
    }

    public void verifyCode(String email, String code) {
        String storedCode = codeStorage.get(email);

        if (!storedCode.equals(code)) {
            System.out.println("인증코드가 다름");
            throw new IllegalStateException("인증코드가 다릅니다.");
        }
        System.out.println("인증성공");
        verifiedEmails.add(email);

    }

    public boolean isVerified(String email) {
        return verifiedEmails.contains(email);
    }

}
