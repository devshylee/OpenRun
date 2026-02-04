package com.openrun.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openrun.core.domain.product.ProductOption;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    /**
     * 상품 ID로 옵션 목록 조회
     */
    List<ProductOption> findByProductId(Long productId);
}
