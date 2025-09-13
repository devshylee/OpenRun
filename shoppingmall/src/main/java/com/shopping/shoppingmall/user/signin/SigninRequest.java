package com.shopping.shoppingmall.user.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
	
    @Schema(description = "사용자 이메일", example = "kawaiinu@example.com")
    private String email;
    
    @Schema(description = "비밀번호", example = "kawaiinu123")
    private String password;
}
