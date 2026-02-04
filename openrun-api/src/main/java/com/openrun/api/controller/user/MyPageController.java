package com.openrun.api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openrun.api.service.user.MyPageService;
import com.openrun.common.dto.ApiResponse;
import com.openrun.core.domain.user.User;
import com.openrun.core.domain.user.UserDetailsImpl;
import com.openrun.core.dto.MyPageDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "MyPage", description = "마이페이지 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<User>> myPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }

        User user = myPageService.findUser(userDetails);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @Operation(summary = "마이페이지 수정", description = "로그인한 사용자의 정보를 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @PutMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> update(
            @Valid @RequestBody MyPageDTO myPageDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }

        myPageService.update(myPageDTO, userDetails);
        return ResponseEntity.ok(ApiResponse.success(null, "정보가 수정되었습니다."));
    }

    @Operation(summary = "내 게시물 조회", description = "로그인한 사용자가 작성한 게시물을 조회합니다.")
    @GetMapping("/myposts")
    public ResponseEntity<ApiResponse<Void>> myPosts() {
        // TODO: 게시물 조회 로직 구현 필요
        return ResponseEntity.ok(ApiResponse.success(null, "게시물 조회 (미구현)"));
    }

    @Operation(summary = "즐겨찾기 조회", description = "로그인한 사용자의 즐겨찾기 목록을 조회합니다.")
    @GetMapping("/myfavorites")
    public ResponseEntity<ApiResponse<Void>> myFavorites() {
        // TODO: 즐겨찾기 조회 로직 구현 필요
        return ResponseEntity.ok(ApiResponse.success(null, "즐겨찾기 조회 (미구현)"));
    }
}
