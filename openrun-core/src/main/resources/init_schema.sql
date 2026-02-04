CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- 회원 고유 ID
    username VARCHAR(50) NOT NULL UNIQUE,           -- 사용자 ID
    password VARCHAR(255) NOT NULL,                 -- 비밀번호 (암호화 저장)
    name VARCHAR(50) NOT NULL,                      -- 이름
    email VARCHAR(100) NOT NULL UNIQUE,             -- 이메일 주소
    phone VARCHAR(20),                              -- 휴대폰 번호
    address VARCHAR(255),                           -- 기본 주소
    address_detail VARCHAR(255),                    -- 상세 주소
    gender VARCHAR(10),                             -- 성별 (예: male/female)
    birth DATE,                                     -- 생년월일
    join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 가입일
    member_grade VARCHAR(20) DEFAULT 'GENERAL',     -- 회원 등급 (예: GENERAL, VIP)
);

-- 카테고리 테이블
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,              -- 카테고리 고유 ID
    name VARCHAR(100) NOT NULL,                     -- 카테고리 이름
    parent_id INT,                                  -- 상위 카테고리 ID (자기 참조)
    FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE CASCADE
);

-- 상품 테이블
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- 상품 고유 ID
    category_id INT NOT NULL,                       -- 카테고리 ID
    name VARCHAR(200) NOT NULL,                     -- 상품명
    description TEXT,                               -- 상품 설명
    price DECIMAL(10, 2) NOT NULL,                  -- 기본 가격
    status VARCHAR(20) NOT NULL DEFAULT 'ON_SALE',  -- 판매 상태 (ON_SALE, SOLD_OUT, STOPPED)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성일
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT
);

-- 상품 옵션 테이블 (SKU)
CREATE TABLE IF NOT EXISTS product_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- 상품 옵션 고유 ID
    product_id BIGINT NOT NULL,                     -- 상품 ID
    option_name VARCHAR(100) NOT NULL,              -- 옵션명 (예: [L] Red)
    additional_price INT NOT NULL DEFAULT 0,        -- 추가 금액
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- 상품 재고 테이블 (동시성 제어를 위한 분리)
CREATE TABLE IF NOT EXISTS product_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- 재고 고유 ID
    product_option_id BIGINT NOT NULL UNIQUE,       -- 상품 옵션 ID (1:1)
    quantity INT NOT NULL DEFAULT 0,                -- 재고 수량
    FOREIGN KEY (product_option_id) REFERENCES product_option(id) ON DELETE CASCADE
);

