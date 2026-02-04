package com.openrun.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 옵션 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionDto {
    private Long id;
    private String optionName;
    private Integer additionalPrice;
    private Integer quantity;
}
