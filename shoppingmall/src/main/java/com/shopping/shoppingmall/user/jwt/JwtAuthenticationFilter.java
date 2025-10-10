package com.shopping.shoppingmall.user.jwt;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shopping.shoppingmall.user.domain.User;
import com.shopping.shoppingmall.user.domain.UserDetailsImpl;
import com.shopping.shoppingmall.user.signin.SigninMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SigninMapper signinMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        //System.out.println("Extracted Token: " + token); // 토큰 값이 null인지 확인

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmail(token);
            User user = signinMapper.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("사용자 없음"));

            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            //System.out.println("JwtAuthenticationFilter : " + userDetails.getUsername());
            //System.out.println("JwtAuthenticationFilter : " + userDetails.getPassword());
            //System.out.println("JwtAuthenticationFilter : " + userDetails.getAuthorities());
            
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }

        filterChain.doFilter(request, response);
    }

    // private String resolveToken(HttpServletRequest request) {
    //     String bearer = request.getHeader("Authorization");
    //     if (bearer != null && bearer.startsWith("Bearer ")) {
    //         return bearer.substring(7);
    //     }
    //     return null;
    // }
    // resolveToken 메서드의 예상 코드
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ACCESS_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
}
}

