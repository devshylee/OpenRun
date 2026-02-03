# 개체 관계 다이어그램 (ERD)

이 다이어그램은 **Open-Run** 커머스 플랫폼의 데이터 모델을 나타내며, 특히 "상품 - 옵션 - 재고" 구조와 핵심 커머스 흐름에 중점을 둡니다.

```mermaid
erDiagram
    %% 회원 도메인 (User Domain)
    USERS {
        bigint id PK "자동 증가 (Auto Increment)"
        varchar username UK
        varchar password
        varchar name
        varchar email UK
        varchar phone
        varchar address
        varchar address_detail
        varchar gender
        date birth
        varchar member_grade
        boolean is_deleted
        timestamp join_date
    }

    %% 카테고리 도메인 (Category Domain)
    CATEGORY {
        int id PK
        varchar name
        int parent_id FK "자기 참조 (Self Reference)"
    }

    %% 상품 도메인 (Product Domain)
    PRODUCT {
        bigint id PK
        bigint category_id FK
        varchar name
        text description
        decimal price "기본 가격 (Base Price)"
        varchar status "판매상태 (ON_SALE, SOLD_OUT, STOPPED)"
        timestamp created_at
        timestamp updated_at
    }

    %% 상품 옵션 (Product Option / SKU)
    %% 실제 판매되는 단위 변동사항 (예: 빨간색 티셔츠 L 사이즈)
    PRODUCT_OPTION {
        bigint id PK
        bigint product_id FK
        varchar option_name "표시명 (예: [L] Red)"
        int additional_price "옵션 추가금 (Extra cost)"
    }

    %% 동시성 제어를 위한 재고 분리 (Stock separation)
    PRODUCT_STOCK {
        bigint id PK
        bigint product_option_id FK
        int quantity "가용 재고 (Available Stock)"
    }

    %% 주문 도메인 (Order Domain)
    ORDERS {
        bigint id PK
        bigint user_id FK
        decimal total_price
        varchar status "주문상태 (PENDING, PAID, SHIPPED, CANCELLED)"
        varchar recipient_name
        varchar recipient_phone
        varchar recipient_address
        timestamp created_at
        timestamp updated_at
    }

    ORDER_ITEM {
        bigint id PK
        bigint order_id FK
        bigint product_option_id FK
        decimal order_price "구매 시점 가격"
        int count
    }

    %% 장바구니 (Cart)
    CART {
        bigint id PK
        bigint user_id FK
        timestamp updated_at
    }

    CART_ITEM {
        bigint id PK
        bigint cart_id FK
        bigint product_option_id FK
        int count
    }

    %% 관계 정의 (Relationships)
    USERS ||--o{ ORDERS : "주문한다 (places)"
    USERS ||--|| CART : "가진다 (has)"
    CATEGORY ||--o{ CATEGORY : "상위카테고리 (parent)"
    CATEGORY ||--o{ PRODUCT : "포함한다 (contains)"
    PRODUCT ||--o{ PRODUCT_OPTION : "가진다 (has)"
    PRODUCT_OPTION ||--|| PRODUCT_STOCK : "재고를 가진다 (stock_of)"
    ORDERS ||--o{ ORDER_ITEM : "포함한다 (contains)"
    CART ||--o{ CART_ITEM : "포함한다 (contains)"
    PRODUCT_OPTION ||--o{ ORDER_ITEM : "참조된다 (reference)"
    PRODUCT_OPTION ||--o{ CART_ITEM : "참조된다 (reference)"
```

## 핵심 설계 원칙 (`Product` - `Option` - `Stock`)

1.  **상품 (Product)**: 상품의 공통 정보(이름, 기본 가격, 설명)를 관리합니다.
    - _예시_: "시원한 여름 티셔츠", 기본가: 10,000원
2.  **상품 옵션 (ProductOption)**: 실제 구매 가능한 단위(SKU)를 나타냅니다.
    - _예시_: 옵션 A (빨강, L, +0원), 옵션 B (파랑, XL, +2,000원)
    - _참고_: Phase 2에서는 색상/사이즈 등의 속성 그룹으로 더 정규화할 수 있지만, 대규모 트래픽이 몰리는 "오픈런" 시나리오에서는 단일 옵션 테이블이 가격 계산 및 유효성 검사에 효율적입니다.
3.  **상품 재고 (ProductStock)**: 동시성 이슈를 최소화하기 위해 `ProductOption`에서 분리했습니다. `quantity` 필드를 별도 테이블로 격리함으로써, 상품 상세 정보를 조회하는 트래픽 락(Lock) 경합 없이 재고 차감 로직(Optimistic Lock 또는 Redis 활용)에만 집중할 수 있습니다.
