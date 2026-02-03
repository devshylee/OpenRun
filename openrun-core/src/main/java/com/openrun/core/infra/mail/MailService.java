package com.openrun.core.infra.mail;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        log.debug("생성된 인증 코드: email={}, code={}", to, code);

    }

    public String generateCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        codeStorage.put(email, code);
        return code;
    }

    public void verifyCode(String email, String code) {
        String storedCode = codeStorage.get(email);

        if (storedCode == null || !storedCode.equals(code)) {
            log.warn("인증 코드 불일치: email={}", email);
            throw new IllegalStateException("인증코드가 다릅니다.");
        }
        log.info("이메일 인증 성공: email={}", email);
        verifiedEmails.add(email);

    }

    public boolean isVerified(String email) {
        return verifiedEmails.contains(email);
    }

}
