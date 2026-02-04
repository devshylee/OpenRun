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
@Table(name = "product_option")
public class ProductOption {

    /** 상품 옵션 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 상품 ID */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /** 옵션명 (예: [L] Red) */
    @Column(name = "option_name", nullable = false, length = 100)
    private String optionName;

    /** 추가 금액 */
    @Column(name = "additional_price", nullable = false)
    @Builder.Default
    private Integer additionalPrice = 0;
}
