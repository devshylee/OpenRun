package com.shopping.shoppingmall.user.signin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shopping.shoppingmall.redis.JwtRedisService;
import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSigninService {
	
	// private final UserSigninRepository userRepository; -> Mapper
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtRedisService redisService;

	// public SigninResponse login(SigninRequest request) {
	// 	User user = userRepository.findByEmail(request.getEmail())
	// 			.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

	// 	if (!user.getIsActive()) {
	// 		throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
	// 	}

	// 	if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	// 		System.out.println("요청한 비밀번호 : " + request.getPassword() + "\n" + "유저 비밀번호 : " + user.getPassword());
	// 		throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
	// 	}

	// 	String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail());
	// 	String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

	// 	redisService.saveRefreshToken(user.getId(), refreshToken);

	// 	return new SigninResponse(accessToken, refreshToken);
	// }

	// 	public void logoutAllDevices(Long userId) {
	// 	redisService.deleteRefreshToken(userId);
	// }
}
