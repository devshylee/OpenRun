# 🚀 실행 가이드

## 로컬 개발 환경 실행

### 1. 필수 조건

- Java 17+
- MySQL 8.0+
- Redis 7+
- Node.js 18+ (프론트엔드)

### 2. 설정 파일 준비

`application-local.properties` 파일이 이미 생성되어 있습니다:

```
openrun-api/src/main/resources/application-local.properties
```

> ⚠️ **중요**: 이 파일은 Git에 커밋되지 않습니다 (.gitignore 포함)

**메일 비밀번호 설정** (선택사항):

```properties
# application-local.properties 파일에 추가
spring.mail.password=your-gmail-app-password
```

Gmail 앱 비밀번호 생성: https://support.google.com/accounts/answer/185833

### 3. 인프라 실행

#### Docker 사용 (권장)

```bash
# docker-compose.yml 파일 이용
docker-compose up -d
```

#### 또는 로컬 설치

```bash
# MySQL (port 3306)
# 데이터베이스: mallproject
# 사용자: shoppingmall / 비밀번호: 1234

# Redis (port 6379)
```

### 4. 백엔드 실행

```bash
# 로컬 프로파일로 실행
./gradlew :openrun-api:bootRun --args='--spring.profiles.active=local'
```

서버 실행 확인:

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

### 5. 프론트엔드 실행 (선택사항)

```bash
cd frontend
npm install
npm run dev
```

---

## 📁 프로젝트 구조

```
open-run/
├── openrun-common/      # 공통 유틸리티
├── openrun-core/        # 도메인 & 인프라 (JPA, Redis, MyBatis)
├── openrun-api/         # 사용자 REST API
├── openrun-admin/       # 관리자 서버
└── frontend/            # React/Vue 프론트엔드
```

---

## 🔐 보안 설정

민감한 정보는 `application-local.properties`에 저장됩니다:

- ✅ DB 비밀번호
- ✅ JWT 시크릿
- ✅ 이메일 계정 정보

상세한 보안 가이드: [SECURITY.md](SECURITY.md)

---

## 🛠️ 개발자 도구

### API 문서

- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs (JSON): http://localhost:8080/api-docs

### 빌드

```bash
# 전체 빌드
./gradlew clean build

# 특정 모듈 빌드
./gradlew :openrun-api:build
```

### 테스트

```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :openrun-api:test
```

---

## ❓ 문제 해결

### 빌드 실패

```bash
# Gradle 캐시 정리
./gradlew clean
rm -rf .gradle build
```

### DB 연결 실패

- MySQL 서버 실행 확인: `netstat -an | findstr 3306`
- 계정 정보 확인: `application-local.properties`

### Redis 연결 실패

- Redis 서버 실행 확인: `netstat -an | findstr 6379`

---

## 📚 추가 문서

- [보안 가이드](SECURITY.md)
- [프로젝트 구조 상세](docs/)
- [API 명세](http://localhost:8080/swagger-ui.html)

---

## 🤝 기여하기

1. 새 기능 브랜치 생성
2. 변경사항 커밋
3. Pull Request 생성

---

## ⚖️ 라이선스

This project is licensed under the MIT License.
