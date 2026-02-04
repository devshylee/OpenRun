package com.openrun.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 옵션 생성 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionRequest {
    private String optionName;
    private Integer additionalPrice;
    private Integer quantity;
}
