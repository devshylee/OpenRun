package com.shopping.shoppingmall.user.mypage;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.domain.UserDetailsImpl;
import com.shopping.shoppingmall.user.signin.SigninMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final SigninMapper signinMapper;
    private final MyPageMapper myPageMapper;

    public User findUser(UserDetailsImpl userDetails){

        Optional<User> optionalUser = signinMapper.findByEmail(userDetails.getUsername());
      
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // 사용자가 없는 경우
            user = null; // 또는 null 처리 로직
        }
        
        
        return user;
    }

    public void update(MyPageDTO myPageDTO, UserDetailsImpl userDetails){
        
        Optional<User> optionalUser = signinMapper.findByEmail(userDetails.getUsername());
        System.out.println("user탐색 완료");

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // 사용자가 없는 경우
            user = null; // 또는 null 처리 로직
        }

        System.out.println("Update의 User" + user);
        myPageMapper.updateMember(myPageDTO, user.getEmail());
    }

}
