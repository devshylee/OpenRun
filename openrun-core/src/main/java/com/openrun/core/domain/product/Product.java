package com.openrun.core.domain.product;

import java.math.BigDecimal;
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
@Table(name = "product")
public class Product {

    /** 상품 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 카테고리 ID */
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    /** 상품명 */
    @Column(nullable = false, length = 200)
    private String name;

    /** 상품 설명 */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** 기본 가격 */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** 판매 상태 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ProductStatus status = ProductStatus.ON_SALE;

    /** 생성일 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 수정일 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = ProductStatus.ON_SALE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
