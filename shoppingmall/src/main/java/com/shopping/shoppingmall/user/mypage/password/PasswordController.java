package com.shopping.shoppingmall.user.mypage.password;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.shoppingmall.user.domain.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @GetMapping("/password")
    public String changePasswordView(){
        return "user/password";
    }

    @PostMapping("/password")
    public String changePassword(@Valid @ModelAttribute("password") PasswordDTO passwordDTO, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){

        if (userDetails == null) {
            return "redirect:/"; // 로그인 안 돼 있으면 홈으로 리다이렉트
        }

        int updatePassword = passwordService.changePassword(userDetails.getId(), passwordDTO);

        if(updatePassword == 0){
             model.addAttribute("successChangePassword", "성공적으로 비밀번호가 변경되었습니다.");
        }else if(updatePassword == 1){
            model.addAttribute("notEqualCurrentPassword", "현재 비밀번호가 올바르지 않습니다.");
        }else if(updatePassword == 2){
            model.addAttribute("notEqualNewPassword", "새 비밀번호가 일치하지 않습니다.");
        }else{
            model.addAttribute("errorChangePassword", "비밀번호 변경 중 문제가 발생하였습니다.");
        }

        return "user/password";

    }
}
