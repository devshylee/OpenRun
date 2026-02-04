package com.openrun.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openrun.core.domain.product.ProductStock;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    /**
     * 상품 옵션 ID로 재고 조회
     */
    Optional<ProductStock> findByProductOptionId(Long productOptionId);
}
