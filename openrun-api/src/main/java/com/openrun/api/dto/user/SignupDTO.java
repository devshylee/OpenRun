package com.openrun.api.dto.user;

import java.time.LocalDate;

import com.openrun.common.validation.PasswordMatch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatch(passwordField = "password", confirmPasswordField = "confirmPassword")
public class SignupDTO {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요..")
    private String password;

    @NotBlank(message = "비밀번호 확인을 위해 입력해주세요.")
    private String confirmPassword;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^0(1[016789]|2|3[1-3]|4[1-4]|5[1-5]|6[1-4]|70|80)[-]?\\d{3,4}[-]?\\d{4}$", message = "유효하지 않은 전화번호 형식입니다.")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "상세주소를 입력해주세요.")
    private String addressDetail;

    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;

    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;

}
