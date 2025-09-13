package com.shopping.shoppingmall.user.signout;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Signout", description = "로그아웃 API")
@RestController
public class UserSignoutController {
	
    @Operation(summary = "로그아웃", description = "JWT토큰을 블랙리스트에 등록하여 로그아웃을 진행")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (필수값 누락 또는 형식 오류)"),
        @ApiResponse(responseCode = "422", description = "입력값 유효성 검증 실패")
    })
	@PostMapping
	public ResponseEntity<String> signout() {
		return ResponseEntity.ok("로그아웃 성공");
	}
}
