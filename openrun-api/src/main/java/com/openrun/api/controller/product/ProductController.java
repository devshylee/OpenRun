package com.openrun.api.controller.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openrun.api.dto.product.ProductCreateRequest;
import com.openrun.api.dto.product.ProductListResponse;
import com.openrun.api.dto.product.ProductResponse;
import com.openrun.api.service.product.ProductService;
import com.openrun.core.domain.product.ProductStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. (옵션 및 재고 포함)")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 페이징하여 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<ProductListResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductListResponse> products = productService.getProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품 목록을 조회합니다.")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductListResponse>> getProductsByCategory(@PathVariable Integer categoryId) {
        List<ProductListResponse> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품 상세 조회", description = "ID로 상품 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "상품 상태 변경", description = "상품의 판매 상태를 변경합니다.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponse> updateProductStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus status) {
        ProductResponse response = productService.updateProductStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
