package com.shopping.shoppingmall.user.signin;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shopping.shoppingmall.redis.JwtRedisService;
import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SigninService {
	
	private final SigninMapper signinMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtRedisService redisService;

	public SigninResponse login(SigninRequest request) {

		System.out.println("로그인메서드 실행");
		System.out.println("전달받은 객체 내용물 : " + request.getEmail() + " / " + request.getPassword());

		System.out.println("findbyEmail 실행");
		Optional<User> user = signinMapper.findByEmail(request.getEmail());

		if(!user.isPresent()){
			System.out.println("존재하지 않는 이메일");
			return null;
		}

		System.out.println("패스워드 검증 실행");
		if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
			System.out.println("요청한 비밀번호 : " + request.getPassword() + "\n" + "유저 비밀번호 : " + user.get().getPassword());
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		System.out.println("액세스토큰 생성");
		String accessToken = jwtTokenProvider.generateAccessToken(user.get().getId(), user.get().getEmail());
		System.out.println("리프레시토큰 생성");
		String refreshToken = jwtTokenProvider.generateRefreshToken(user.get().getId());

		System.out.println("리프레시토큰 저장");
		redisService.saveRefreshToken(user.get().getId(), refreshToken);

		System.out.println("토큰 반환");
		return new SigninResponse(accessToken, refreshToken);
	}


}
