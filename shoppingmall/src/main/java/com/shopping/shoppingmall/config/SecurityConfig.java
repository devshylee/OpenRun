package com.shopping.shoppingmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shopping.shoppingmall.user.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // ✅ 로그인한 사용자만 글쓰기/수정/삭제 가능
                .requestMatchers("/board/write", "/board/edit/**", "/board/delete/**").authenticated()
                // ✅ 나머지 URL은 모두 허용
                .anyRequest().permitAll()
            )
            // ✅ JWT 필터 등록
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .logout(logout -> logout
            .logoutUrl("/logout") // 로그아웃을 처리할 URL (POST 요청)
            .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트될 URL
            .invalidateHttpSession(true) // HTTP 세션 무효화
            .deleteCookies("ACCESS_TOKEN", "REFRESH_TOKEN") // 쿠키 삭제
        );

        return http.build();
    }
}

