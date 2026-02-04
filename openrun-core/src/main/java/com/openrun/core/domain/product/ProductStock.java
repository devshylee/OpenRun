package com.openrun.core.domain.product;

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
@Table(name = "product_stock")
public class ProductStock {

    /** 재고 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 상품 옵션 ID */
    @Column(name = "product_option_id", nullable = false, unique = true)
    private Long productOptionId;

    /** 재고 수량 */
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;
}
