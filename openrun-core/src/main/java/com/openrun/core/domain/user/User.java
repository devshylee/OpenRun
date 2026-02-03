package com.openrun.core.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class User {

    /** 회원 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 사용자 ID */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /** 비밀번호 */
    @Column(nullable = false)
    private String password;

    /** 이름 */
    @Column(nullable = false, length = 50)
    private String name;

    /** 이메일 주소 */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /** 휴대폰 번호 */
    @Column(length = 20)
    private String phone;

    /** 기본 주소 */
    private String address;

    /** 상세 주소 */
    private String addressDetail;

    /** 성별 */
    @Column(length = 10)
    private String gender;

    /** 생년월일 */
    private LocalDate birth;

    /** 가입일 */
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    /** 회원 등급 */
    @Column(name = "member_grade", length = 20)
    private String memberGrade;

    /** 탈퇴 여부 */
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @PrePersist
    public void prePersist() {
        if (this.joinDate == null)
            this.joinDate = LocalDateTime.now();
        if (this.memberGrade == null)
            this.memberGrade = "GENERAL";
        if (this.isDeleted == null)
            this.isDeleted = false;
    }
}
