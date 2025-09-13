// package com.kawaiinu.user.signup;

// import static org.mockito.ArgumentMatchers.argThat;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.crypto.password.PasswordEncoder;

// class UserSignupServiceTest {

//     private UserSignupRepository userRepository;
//     private PasswordEncoder passwordEncoder;
//     private UserSignupFilterService filterService;
//     private UserSignupService signupService;

//     @BeforeEach
//     void setUp() {
//         userRepository = mock(UserSignupRepository.class);
//         passwordEncoder = mock(PasswordEncoder.class);
//         filterService = mock(UserSignupFilterService.class);

//         signupService = new UserSignupService(userRepository, passwordEncoder, filterService);
//     }

//     @Test
//     @DisplayName("회원가입 시 필터링과 저장 로직이 정상 작동하는지 확인")
//     void shouldSignupUserSuccessfully() {
//         // given
//         SignupRequest request = SignupRequest.builder()
//             .nickname("카와이이누")
//             .email("test@kawaiinu.app")
//             .password("securePass123!")
//             .gender("F")
//             .birth(LocalDate.of(2000, 1, 1))
//             .profile_image(1)
//             .build();

//         String encodedPassword = "encodedPassword123!";
//         when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

//         // when
//         signupService.signup(request);

//         // then
//         verify(filterService).validate(request);
//         verify(passwordEncoder).encode(request.getPassword());
//         verify(userRepository).save(argThat(user ->
//             user.getNickname().equals(request.getNickname())
//             && user.getEmail().equals(request.getEmail())
//             && user.getPassword().equals(encodedPassword)
//             && user.getGender().equals(request.getGender())
//             && user.getBirth().equals(request.getBirth())
//             && user.getProfileImageId().equals(request.getProfile_image())
//             && user.getIsActive()
//         ));
//     }
// }
