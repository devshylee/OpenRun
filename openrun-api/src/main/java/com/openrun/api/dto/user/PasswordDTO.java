package com.openrun.api.dto.user;

import com.openrun.common.validation.PasswordMatch;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch(passwordField = "newPassword", confirmPasswordField = "confirmPassword")
public class PasswordDTO {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "변경할 비밀번호를 다시 입력해주세요.")
    private String confirmPassword;
}
