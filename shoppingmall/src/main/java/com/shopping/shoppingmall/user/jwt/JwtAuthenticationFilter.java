// package com.shopping.shoppingmall.user.jwt;


// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.kawaiinu.user.User;
// import com.kawaiinu.user.UserDetailsImpl;
// import com.kawaiinu.user.signin.UserSigninRepository;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtTokenProvider jwtTokenProvider;
//     private final UserSigninRepository userRepository;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain) throws ServletException, IOException {
//         String token = resolveToken(request);

//         if (token != null && jwtTokenProvider.validateToken(token)) {
//             String email = jwtTokenProvider.getEmail(token);
//             User user = userRepository.findByEmail(email)
//                     .orElseThrow(() -> new RuntimeException("사용자 없음"));

//             UserDetailsImpl userDetails = new UserDetailsImpl(user);

//             UsernamePasswordAuthenticationToken authentication =
//                     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }

//         filterChain.doFilter(request, response);
//     }

//     private String resolveToken(HttpServletRequest request) {
//         String bearer = request.getHeader("Authorization");
//         if (bearer != null && bearer.startsWith("Bearer ")) {
//             return bearer.substring(7);
//         }
//         return null;
//     }
// }

