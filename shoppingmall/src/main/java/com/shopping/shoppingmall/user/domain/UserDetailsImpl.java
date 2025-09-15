package com.shopping.shoppingmall.user.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;   

// import java.util.Collection;
// import java.util.List;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImpl implements UserDetails{
	
	private final Long id;
    private final String email;
    private final String password;
    private final boolean isActive;
    // private final int role;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isActive = user.getIsDeleted();
        // this.role = user.getRole();
    }

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     // 권한 설정: role 1 = USER, 2 = ADMIN
    //     String roleName = switch (role) {
    //         case 1 -> "ROLE_USER";
    //         case 2 -> "ROLE_ADMIN";
    //         default -> "ROLE_GUEST";
    //     };
    //     return List.of(new SimpleGrantedAuthority(roleName));
    // }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // 권한 리스트 (필요 시 user.getRole() 기반으로 추가)
    }

    @Override
    public String getUsername() {
        return email; // 로그인 ID로 사용되는 필드
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
