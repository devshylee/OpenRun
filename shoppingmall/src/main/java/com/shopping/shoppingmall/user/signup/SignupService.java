package com.shopping.shoppingmall.user.signup;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.shoppingmall.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final SignupMapper signupMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void signup(SignupRequest request){

        int result = signupMapper.findUserById(request.getUserId());

        if(result > 0){
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new IllegalStateException("확인된 비밀번호가 다릅니다.");
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
