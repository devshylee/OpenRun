package com.shopping.shoppingmall.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    /** 회원 고유 ID (id BIGSERIAL PRIMARY KEY) */
    private Long id;
    
    /** 사용자 ID (username VARCHAR(50) NOT NULL UNIQUE) */
    private String username;
    
    /** 비밀번호 (password VARCHAR(255) NOT NULL) */
    private String password;
    
    /** 이름 (name VARCHAR(50) NOT NULL) */
    private String name;
    
    /** 이메일 주소 (email VARCHAR(100) NOT NULL UNIQUE) */
    private String email;
    
    /** 휴대폰 번호 (phone VARCHAR(20)) */
    private String phone;
    
    /** 기본 주소 (address VARCHAR(255)) */
    private String address;
    
    /** 상세 주소 (address_detail VARCHAR(255)) */
    private String addressDetail; // address_detail -> addressDetail (자바 표기법)
    
    /** 성별 (gender VARCHAR(10)) */
    private String gender;

    // 생년월일
    private LocalDate birth;
    
    /** 가입일 (join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP) */
    private LocalDateTime joinDate; // join_date -> joinDate
    
    /** 회원 등급 (member_grade VARCHAR(20) DEFAULT 'GENERAL') */
    private String memberGrade; // member_grade -> memberGrade
    
    /** 탈퇴 여부 (is_deleted BOOLEAN DEFAULT FALSE) */
    private Boolean isDeleted; // is_deleted -> isDeleted
}
