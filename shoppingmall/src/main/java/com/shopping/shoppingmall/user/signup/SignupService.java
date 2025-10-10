package com.shopping.shoppingmall.user.signup;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.mail.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final SignupMapper signupMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void signup(SignupDTO request){

        int result = signupMapper.findUserById(request.getUserId());

        if(result > 0){
            System.out.println("이미 사용중인 아이디");
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            System.out.println("비밀번호 다름");
            throw new IllegalStateException("확인된 비밀번호가 다릅니다.");
        }
        
        if (!mailService.isVerified(request.getEmail())) {
            System.out.println("이메일인증 미완료");
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
        .username(request.getUserId())
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
