package com.openrun.api.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openrun.core.domain.user.User;
import com.openrun.core.infra.mail.MailService;
import com.openrun.core.mapper.user.SignupMapper;
import com.openrun.api.dto.user.SignupDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final SignupMapper signupMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void signup(SignupDTO request) {

        int result = signupMapper.findUserById(request.getUsername());

        if (result > 0) {
            throw new com.openrun.common.exception.DuplicateUserIdException("이미 사용 중인 아이디입니다.");
        }

        if (!mailService.isVerified(request.getEmail())) {
            throw new com.openrun.common.exception.EmailNotVerifiedException("이메일 인증이 완료되지 않았습니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .birth(request.getBirth())
                .gender(request.getGender())
                .isDeleted(false)
                .build();

        signupMapper.insertUser(user);
    }

}
