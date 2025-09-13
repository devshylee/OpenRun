package com.shopping.shoppingmall.user.signin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResponse {
	
    private String accessToken;
    private String refreshToken;
    
}
