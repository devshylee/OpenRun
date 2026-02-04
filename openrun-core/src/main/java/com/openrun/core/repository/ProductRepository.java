package com.openrun.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openrun.core.domain.product.Product;
import com.openrun.core.domain.product.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 카테고리 ID로 상품 조회
     */
    List<Product> findByCategoryId(Integer categoryId);

    /**
     * 판매 상태로 상품 조회
     */
    List<Product> findByStatus(ProductStatus status);
}
