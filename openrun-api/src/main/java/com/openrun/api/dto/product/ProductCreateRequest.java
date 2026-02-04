package com.openrun.api.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 생성 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {
    private String name;
    private String description;
    private Integer categoryId;
    private Double price;
    private List<ProductOptionRequest> options;
}
