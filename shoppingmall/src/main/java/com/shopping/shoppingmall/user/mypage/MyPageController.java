package com.shopping.shoppingmall.user.mypage;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.domain.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // userDetails에 null이 할당되어서 에러발생함.
    // AuthenticationPrincipal  동작원리 알아보기
    // 로그인하고 새로고침하면 다시 로그인버튼나옴 -> 모델기반 데이터전달은 새로고침하면 사라짐 -> 세션 혹은 쿠키기반으로 하도록 변경

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       
        if (userDetails == null) {
            return "redirect:/"; // 로그인 안 돼 있으면 홈으로 리다이렉트
        }
          
        User user = myPageService.findUser(userDetails);
        // user가 null일 경우 예외처리 추가예정
      
        model.addAttribute("user", user);
   
        return "user/mypage"; // mypage.html 렌더링
    }

    @PostMapping("/mypage")
    public String update(@Valid @ModelAttribute("user") MyPageDTO myPageDTO,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails){

        System.out.println("mypage post 요청");

        System.out.println("DTO : " + myPageDTO.getUsername());
        System.out.println("DTO : " + myPageDTO.getName());
        System.out.println("DTO : " + myPageDTO.getEmail());
        System.out.println("DTO : " + myPageDTO.getPhone());
        System.out.println("DTO : " + myPageDTO.getAddress());
        System.out.println("DTO : " + myPageDTO.getAddressDetail());
        System.out.println("DTO : " + myPageDTO.getGender());
        if (userDetails == null) {
            return "redirect:/"; // 로그인 안 돼 있으면 홈으로 리다이렉트
        }

        // if (bindingResult.hasErrors()) {
        //     return "user/mypage";
        // }
        
        System.out.println("update 실행 전");
        myPageService.update(myPageDTO, userDetails);
        System.out.println("update 실행 완료");
        return "user/mypage";
    }



    @GetMapping("/myposts")
    public String myPosts() {
        return "user/myposts-dummy";
    }

    @GetMapping("/myfavorites")
    public String myFavorites(){
        return "user/myfavorites";
    }
    
}

