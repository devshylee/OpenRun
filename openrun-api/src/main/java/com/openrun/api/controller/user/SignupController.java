package com.openrun.api.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.api.dto.user.SignupDTO;
import com.openrun.api.service.user.SignupService;
import com.openrun.common.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Signup", description = "회원가입 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복된 아이디")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody SignupDTO signupRequest) {
        signupService.signup(signupRequest);
        return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다."));
    }
}
