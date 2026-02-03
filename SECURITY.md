# 보안 및 환경 설정 가이드

## ⚠️ 중요: GitHub Push 전 필수 확인사항

### 현재 보안 이슈

`application.properties` 파일에 **민감한 정보가 하드코딩**되어 있습니다:

- 데이터베이스 비밀번호
- JWT 시크릿 키
- 이메일 계정 정보

### 해결 방법

#### Option 1: 환경 변수 사용 (권장)

1. **`.env` 파일 생성** (로컬 개발용)

   ```bash
   cp .env.example .env
   # .env 파일 편집하여 실제 값 입력
   ```

2. **`application.properties` 수정**

   ```properties
   spring.datasource.password=${DB_PASSWORD}
   jwt.secret=${JWT_SECRET}
   spring.mail.password=${MAIL_PASSWORD}
   ```

3. **환경 변수 설정 후 실행**

   ```bash
   # Windows PowerShell
   $env:DB_PASSWORD="1234"
   $env:JWT_SECRET="your-secret"
   ./gradlew :openrun-api:bootRun

   # Windows CMD
   set DB_PASSWORD=1234
   set JWT_SECRET=your-secret
   gradlew :openrun-api:bootRun
   ```

#### Option 2: Profile 분리

1. **`application-local.properties` 생성** (Git에 커밋되지 않음)

   ```properties
   spring.datasource.password=1234
   jwt.secret=ZmFrZVNlY3JldEtleUhlcmUzMkJ5dGVzMTIzNDU2
   spring.mail.password=your-app-password
   ```

2. **실행 시 프로파일 지정**
   ```bash
   ./gradlew :openrun-api:bootRun --args='--spring.profiles.active=local'
   ```

---

## 로컬 개발 환경 설정

### 1. 인프라 실행 (Docker 권장)

**docker-compose.yml** 생성:

```yaml
version: "3.8"
services:
  mysql:
    image: mysql:8.0
    container_name: openrun-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root1234
      MYSQL_DATABASE: mallproject
      MYSQL_USER: shoppingmall
      MYSQL_PASSWORD: 1234
    ports:
      - "3306:3306"

  redis:
    image: redis:7-alpine
    container_name: openrun-redis
    ports:
      - "6379:6379"
```

실행:

```bash
docker-compose up -d
```

### 2. JWT Secret 생성

```bash
# Linux/Mac
openssl rand -base64 32

# Windows (PowerShell)
[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))
```

### 3. Gmail 앱 비밀번호 생성

1. [Google 계정 관리](https://myaccount.google.com/) 접속
2. 보안 > 2단계 인증 활성화
3. 앱 비밀번호 생성
4. 16자리 비밀번호를 `MAIL_PASSWORD`에 사용

---

## Git 히스토리에서 민감 정보 제거 (선택사항)

**경고**: 이미 커밋된 민감 정보가 있다면 Git 히스토리를 정리해야 합니다.

```bash
# BFG Repo-Cleaner 사용 (권장)
# https://rtyley.github.io/bfg-repo-cleaner/
bfg --replace-text passwords.txt

# 또는 git filter-branch
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch openrun-api/src/main/resources/application.properties" \
  --prune-empty --tag-name-filter cat -- --all
```

---

## 현재 .gitignore 설정

다음 항목들이 Git에서 자동으로 제외됩니다:

### 빌드 산출물

- `build/`, `bin/`, `out/`, `target/`
- `.gradle/`
- `*.class`, `*.jar`, `*.war`

### IDE 파일

- `.idea/`, `*.iml`
- `.vscode/` (일부 설정 파일 제외)
- `.settings/`, `.classpath`, `.project`

### 민감한 설정 파일

- `application-local.properties`
- `application-dev.properties`
- `application-prod.properties`
- `.env`, `.env.local`

### Frontend

- `node_modules/`
- `dist/`

### 기타

- OS 임시 파일 (`.DS_Store`, `Thumbs.db`)
- 로그 파일 (`*.log`)
- Agent 파일 (`.agent/`)

---

## GitHub Push 체크리스트

- [ ] `.gitignore` 파일이 존재하는가?
- [ ] `build/`, `bin/`, `.gradle/` 디렉토리가 무시되는가?
- [ ] `application.properties`에 민감 정보가 없는가?
- [ ] `.env.example`은 포함하되 `.env`는 제외되는가?
- [ ] `git status`로 불필요한 파일이 추적되지 않는지 확인

---

## 팀 협업 시 권장사항

1. **`.env.example` 파일 관리**
   - 모든 필수 환경 변수 나열
   - 실제 값 대신 설명 또는 예시 값 사용
   - 새 팀원 온보딩 시 참고 자료로 활용

2. **문서화**
   - README.md에 환경 설정 가이드 링크
   - 각 환경 변수의 용도와 생성 방법 설명

3. **CI/CD 환경**
   - GitHub Secrets 또는 환경 변수로 관리
   - 각 환경(dev, staging, prod)별로 분리

---

## 문의사항

환경 설정 관련 문제가 있다면:

1. `.env.example` 파일 확인
2. 이 문서의 해결 방법 섹션 참고
3. 팀 리드에게 문의
