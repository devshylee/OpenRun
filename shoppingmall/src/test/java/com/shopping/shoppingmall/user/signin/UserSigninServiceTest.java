// package com.shopping.shoppingmall.user.signin;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.shopping.shoppingmall.user.domain.User;
// import com.shopping.shoppingmall.user.jwt.JwtTokenProvider;

// class UserSigninServiceTest {

//     private UserSigninRepository userRepository;
//     private PasswordEncoder passwordEncoder;
//     private JwtTokenProvider jwtTokenProvider;
//     private UserSigninService signinService;

//     @BeforeEach
//     void setUp() {
//         userRepository = mock(UserSigninRepository.class);
//         passwordEncoder = mock(PasswordEncoder.class);
//         jwtTokenProvider = mock(JwtTokenProvider.class);
//         //signinService = new UserSigninService(userRepository, passwordEncoder, jwtTokenProvider);
//     }

//     @Test
//     @DisplayName("존재하지 않는 이메일일 경우 예외 발생")
//     void shouldThrowWhenEmailNotFound() {
//         SigninRequest request = new SigninRequest("test@example.com", "password1234");
//         when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

//         assertThatThrownBy(() -> signinService.login(request))
//             .isInstanceOf(IllegalArgumentException.class)
//             .hasMessageContaining("가입되지 않은 이메일입니다.");
//     }

//     @Test
//     @DisplayName("이메일 인증이 되지 않은 경우 예외 발생")
//     void shouldThrowWhenEmailNotVerified() {
//         User user = User.builder()
//             .email("test@example.com")
//             .password("encoded")
//             .isActive(false)
//             .build();

//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

//         SigninRequest request = new SigninRequest(user.getEmail(), "password");

//         assertThatThrownBy(() -> signinService.login(request))
//             .isInstanceOf(IllegalStateException.class)
//             .hasMessageContaining("이메일 인증이 완료되지 않았습니다.");
//     }

//     @Test
//     @DisplayName("비밀번호 불일치 시 예외 발생")
//     void shouldThrowWhenPasswordNotMatch() {
//         User user = User.builder()
//             .email("test@example.com")
//             .password("encoded-password")
//             .isActive(true)
//             .build();

//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
//         when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

//         SigninRequest request = new SigninRequest(user.getEmail(), "wrong-password");

//         assertThatThrownBy(() -> signinService.login(request))
//             .isInstanceOf(IllegalArgumentException.class)
//             .hasMessageContaining("비밀번호가 일치하지 않습니다.");
//     }

//     @Test
//     @DisplayName("로그인 성공 시 JWT 토큰 반환")
//     void shouldReturnTokenWhenLoginSuccess() {
//         User user = User.builder()
//             .id(1L)
//             .email("test@example.com")
//             .password("encoded-password")
//             .isActive(true)
//             .build();

//         SigninRequest request = new SigninRequest(user.getEmail(), "plain-password");

//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
//         when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
//         //when(jwtTokenProvider.generateToken(user.getId(), user.getEmail())).thenReturn("mock-jwt-token");

//         SigninResponse response = signinService.login(request);

//         assertThat(response.getAccessToken()).isEqualTo("mock-jwt-token");
//     }
// }
