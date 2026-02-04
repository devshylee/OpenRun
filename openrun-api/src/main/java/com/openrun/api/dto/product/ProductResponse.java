package com.openrun.api.dto.product;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 상세 응답 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer categoryId;
    private Double price;
    private String status;
    private List<ProductOptionDto> options;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
