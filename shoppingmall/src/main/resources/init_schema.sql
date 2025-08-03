CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,                       -- 회원 고유 ID
    username VARCHAR(50) NOT NULL UNIQUE,           -- 사용자 ID
    password VARCHAR(255) NOT NULL,                 -- 비밀번호 (암호화 저장)
    name VARCHAR(50) NOT NULL,                      -- 이름
    email VARCHAR(100) NOT NULL UNIQUE,             -- 이메일 주소
    phone VARCHAR(20),                              -- 휴대폰 번호
    address VARCHAR(255),                           -- 기본 주소
    address_detail VARCHAR(255),                    -- 상세 주소
    gender VARCHAR(10),                             -- 성별 (예: male/female)
    birth DATE,                             -- 생년월일
    agree_terms BOOLEAN NOT NULL DEFAULT FALSE,     -- 필수 약관 동의
    agree_marketing BOOLEAN DEFAULT FALSE,          -- 선택 약관 동의
    join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 가입일
    member_grade VARCHAR(20) DEFAULT 'GENERAL',     -- 회원 등급 (예: GENERAL, VIP)
    is_deleted BOOLEAN DEFAULT FALSE                -- 탈퇴 여부
);