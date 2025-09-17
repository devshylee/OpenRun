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
    join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 가입일
    member_grade VARCHAR(20) DEFAULT 'GENERAL',     -- 회원 등급 (예: GENERAL, VIP)
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE                -- 탈퇴 여부
);

-- CREATE TABLE category (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(50) NOT NULL,
--     parent_id INT NULL,
-- );

-- ALTER TABLE category
--     ADD CONSTRAINT fk_parent_category
--     FOREIGN KEY (parent_id) REFERENCES category (id);

-- CREATE TABLE post (
--     id SERIAL PRIMARY KEY,
--     category_id INT NOT NULL,
--     user_id BIGINT NOT NULL,
--     title VARCHAR(255) NOT NULL,
--     content TEXT NOT NULL,
--     image_url VARCHAR(255),
--     view_count INT DEFAULT 0,
--     like_count INT DEFAULT 0,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

--     CONSTRAINT fk_post_category
--         FOREIGN KEY (category_id) REFERENCES category (id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_post_user
--         FOREIGN KEY (user_id) REFERENCES users (id)
--         ON DELETE CASCADE
-- );

-- CREATE TABLE comment (
--     id SERIAL PRIMARY KEY,
--     post_id INT NOT NULL,
--     user_id BIGINT NOT NULL,
--     parent_id INT NULL,
--     content TEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

--     CONSTRAINT fk_comment_post
--         FOREIGN KEY (post_id) REFERENCES post (id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_parent_comment
--         FOREIGN KEY (parent_id) REFERENCES comment (id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_comment_user
--         FOREIGN KEY (user_id) REFERENCES users (id)
--         ON DELETE CASCADE
-- );


