// package com.kawaiinu.user.signup;

// import static org.assertj.core.api.Assertions.assertThatCode;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;

// import java.time.LocalDate;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// class UserSignupFilterServiceTest {

//     private UserSignupFilterService filterService;

//     @BeforeEach
//     void setUp() {
//         filterService = new UserSignupFilterService();
//     }

//     @Test
//     @DisplayName("닉네임에 비속어가 포함되면 예외 발생")
//     void shouldThrowWhenNicknameContainsBadWord() {
//         assertThatThrownBy(() -> filterService.checkContainBadWord("비속어"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessageContaining("부적절한 단어");
//     }

//     @Test
//     @DisplayName("비밀번호가 생년월일을 포함하면 예외 발생")
//     void shouldThrowWhenPasswordContainsBirth() {
//         LocalDate birth = LocalDate.of(1995, 12, 24);
//         assertThatThrownBy(() -> filterService.validatePassword("password19951224", birth))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessageContaining("생년월일");
//     }

//     @Test
//     @DisplayName("닉네임에 이모지가 포함되면 예외 발생")
//     void shouldThrowWhenNicknameHasEmoji() {
//         assertThatThrownBy(() -> filterService.validateNickname("강아지🐶"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessageContaining("특수문자");
//     }

//     @Test
//     @DisplayName("이메일 형식이 잘못되면 예외 발생")
//     void shouldThrowWhenEmailFormatInvalid() {
//         assertThatThrownBy(() -> filterService.checkEmailFormat("not-an-email"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessageContaining("올바른 이메일 형식");
//     }

//     @Test
//     @DisplayName("미래의 생년월일이면 예외 발생")
//     void shouldThrowWhenBirthInFuture() {
//         assertThatThrownBy(() -> filterService.checkBirthNotInFuture(LocalDate.now().plusDays(1)))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessageContaining("미래일");
//     }

//     @Test
//     @DisplayName("올바른 입력은 예외 없이 통과")
//     void shouldPassValidationWithValidInputs() {
//         SignupRequest request = new SignupRequest(
//                 "dog@kawaiinu.app",
//                 "Password123",
//                 "멍멍이",
//                 1,
//                 LocalDate.of(1990, 1, 1),
//                 "M"
//         );

//         assertThatCode(() -> filterService.validate(request))
//                 .doesNotThrowAnyException();
//     }
// }
