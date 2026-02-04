package com.openrun.api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.api.dto.user.PasswordDTO;
import com.openrun.api.service.user.PasswordService;
import com.openrun.common.dto.ApiResponse;
import com.openrun.core.domain.user.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Password", description = "비밀번호 변경 API")
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "비밀번호 변경", description = "로그인한 사용자의 비밀번호를 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @Valid @RequestBody PasswordDTO passwordDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }

        // 예외가 발생하면 GlobalExceptionHandler가 처리
        passwordService.changePassword(userDetails.getId(), passwordDTO);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 비밀번호가 변경되었습니다."));
    }
}
