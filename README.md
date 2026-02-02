# 🛒 프로젝트: 오픈런 (Open-Run)

> **고성능 확장형 커머스 백엔드 엔진** > 본 프로젝트는 의류 쇼핑몰을 기반으로, 대규모 트래픽과 데이터 환경에서도 안정적인 서비스를 제공하는 백엔드 아키텍처를 지향합니다.

---

## 1. 🎯 프로젝트 핵심 목표

- **도메인 모델링**: 의류 특성(사이즈, 색상)을 고려한 확장성 있는 DB 설계
- **코드 품질**: SOLID 원칙 준수 및 계층화된 아키텍처(Layered Architecture) 적용
- **성능 검증**: 10만 건 이상의 데이터 환경에서 조회 성능 최적화
- **안정성**: JUnit5 기반의 테스트 코드 작성을 통한 비즈니스 로직 신뢰성 확보

---

## 🛠 2. 기술 스택 (Tech Stack)

| 분류            | 기술                           | 역할                                        |
| :-------------- | :----------------------------- | :------------------------------------------ |
| **Framework**   | **Java 17 / Spring Boot 3.x**  | 메인 애플리케이션 프레임워크                |
| **Persistence** | **Spring Data JPA / Querydsl** | 타입 안정성을 갖춘 데이터 접근 및 동적 쿼리 |
| **Database**    | **MySQL 8.0**                  | 데이터 저장 및 인덱스 최적화                |
| **Security**    | **Spring Security / JWT**      | 유저 인증 및 권한 관리                      |
| **Test**        | **JUnit5 / Mockito**           | 단위 및 통합 테스트 코드 작성               |
| **Build Tool**  | **Gradle**                     | 멀티 모듈 기반 의존성 및 빌드 관리          |

---

## 🏗 3. 단계별 고도화 로드맵

### **Phase 1: 기반 구축 및 도메인 설계**

- **멀티 모듈 구성**: `core`(공통), `api`(사용자), `admin`(관리자) 분리
- **ERD 설계**: `Product` - `Option` - `Stock` 간의 정규화된 관계 설계
- **데이터 증폭**: `Fake Store API` + `Java Faker`를 활용한 10만 건의 더미 데이터 구축

### **Phase 2: 비즈니스 로직 및 동적 쿼리**

- **핵심 기능**: 상품 목록(페이징), 장바구니, 주문 생성 로직 구현
- **동적 검색**: Querydsl을 이용한 다중 조건(카테고리, 가격대, 브랜드) 필터링 구현
- **데이터 일관성**: 주문 시 재고 감소 로직 및 트랜잭션 관리

### **Phase 3: 성능 최적화 및 테스트**

- **N+1 문제 해결**: Fetch Join 및 Batch Size 설정을 통한 조회 최적화
- **인덱스 튜닝**: 실행 계획(Explain) 분석을 통한 복합 인덱스 적용 및 성능 비교
- **신뢰성 확보**: 핵심 비즈니스 로직에 대한 테스트 커버리지 확보

### **Phase 4: 인프라 및 고도화 준비**

- **API 문서화**: Swagger(Springdoc)를 이용한 대화형 API 명세서 자동화
- **컨테이너화**: Docker를 활용한 일관된 로컬 개발 및 실행 환경 구축
- **확장 준비**: 이후 'Redis 분산 락' 및 'Elasticsearch 검색' 도입을 위한 인터페이스 추상화

---

## 💡 4. 신입의 차별화 포인트 (Interview Points)

- **멀티 모듈 구조**: 유지보수성과 재사용성을 고려한 실무 지향적 프로젝트 설계
- **데이터 중심적 사고**: 10만 건 이상의 데이터를 직접 생성하고 성능 임계점을 테스트한 경험
- **옵션 테이블 분리**: 의류 품목의 복잡한 옵션 구조를 효율적으로 정규화하여 관리
- **예외 처리 전략**: `@RestControllerAdvice`를 이용한 공통 에러 응답 규격 정의

---

## 📂 5. 프로젝트 구조 (Directory)

```text
open-run/
├── openrun-core/           # Entity, Repository, Domain Logic
├── openrun-api/            # User-facing API Controllers & Services
├── openrun-admin/          # Management API & Statistics
├── openrun-common/         # Global Utilities, DTOs, Exceptions
└── scripts/                # Data Generation & Batch Scripts
```
