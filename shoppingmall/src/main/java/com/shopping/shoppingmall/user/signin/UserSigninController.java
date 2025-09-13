// package com.shopping.shoppingmall.user.signin;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import lombok.RequiredArgsConstructor;



// @Tag(name="Signin", description = "로그인 API")
// @RestController
// @RequestMapping("/api")
// @RequiredArgsConstructor
// public class UserSigninController {
	
//     private final UserSigninService userService;
    
//     @Operation(summary = "회원로그인", description = "이메일/비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
//     @ApiResponses({
//         @ApiResponse(responseCode = "200", description = "로그인 성공"),
//         @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
//     })
//     @PostMapping("/signin")
//     public ResponseEntity<SigninResponse> login(@RequestBody SigninRequest request) {
//         SigninResponse response = userService.login(request);
//         return ResponseEntity.ok(response);
//     }
// }
