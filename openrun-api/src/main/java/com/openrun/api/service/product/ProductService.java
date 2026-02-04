package com.openrun.api.service.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openrun.api.dto.product.ProductCreateRequest;
import com.openrun.api.dto.product.ProductListResponse;
import com.openrun.api.dto.product.ProductOptionDto;
import com.openrun.api.dto.product.ProductOptionRequest;
import com.openrun.api.dto.product.ProductResponse;
import com.openrun.common.exception.CategoryNotFoundException;
import com.openrun.common.exception.ProductNotFoundException;
import com.openrun.core.domain.product.Product;
import com.openrun.core.domain.product.ProductOption;
import com.openrun.core.domain.product.ProductStatus;
import com.openrun.core.domain.product.ProductStock;
import com.openrun.core.repository.CategoryRepository;
import com.openrun.core.repository.ProductOptionRepository;
import com.openrun.core.repository.ProductRepository;
import com.openrun.core.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductStockRepository productStockRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 상품 등록 (Product + ProductOption + ProductStock 한번에)
     */
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // 카테고리 존재 여부 확인
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new CategoryNotFoundException(request.getCategoryId());
        }

        // 1. Product 생성
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .price(BigDecimal.valueOf(request.getPrice()))
                .status(ProductStatus.ON_SALE)
                .build();

        Product savedProduct = productRepository.save(product);

        // 2. ProductOption 및 ProductStock 생성
        List<ProductOptionDto> optionDtos = new ArrayList<>();
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            for (ProductOptionRequest optionReq : request.getOptions()) {
                // 옵션 생성
                ProductOption option = ProductOption.builder()
                        .productId(savedProduct.getId())
                        .optionName(optionReq.getOptionName())
                        .additionalPrice(optionReq.getAdditionalPrice() != null ? optionReq.getAdditionalPrice() : 0)
                        .build();

                ProductOption savedOption = productOptionRepository.save(option);

                // 재고 생성
                ProductStock stock = ProductStock.builder()
                        .productOptionId(savedOption.getId())
                        .quantity(optionReq.getQuantity() != null ? optionReq.getQuantity() : 0)
                        .build();

                productStockRepository.save(stock);

                // DTO 생성
                optionDtos.add(ProductOptionDto.builder()
                        .id(savedOption.getId())
                        .optionName(savedOption.getOptionName())
                        .additionalPrice(savedOption.getAdditionalPrice())
                        .quantity(stock.getQuantity())
                        .build());
            }
        }

        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .categoryId(savedProduct.getCategoryId())
                .price(savedProduct.getPrice().doubleValue())
                .status(savedProduct.getStatus().name())
                .options(optionDtos)
                .createdAt(savedProduct.getCreatedAt())
                .updatedAt(savedProduct.getUpdatedAt())
                .build();
    }

    /**
     * 상품 목록 조회 (페이징)
     */
    public Page<ProductListResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::toListResponse);
    }

    /**
     * 카테고리별 상품 목록 조회
     */
    public List<ProductListResponse> getProductsByCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 상품 상세 조회
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // 옵션 및 재고 조회
        List<ProductOption> options = productOptionRepository.findByProductId(id);
        List<ProductOptionDto> optionDtos = options.stream()
                .map(option -> {
                    ProductStock stock = productStockRepository.findByProductOptionId(option.getId())
                            .orElse(ProductStock.builder().quantity(0).build());

                    return ProductOptionDto.builder()
                            .id(option.getId())
                            .optionName(option.getOptionName())
                            .additionalPrice(option.getAdditionalPrice())
                            .quantity(stock.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategoryId())
                .price(product.getPrice().doubleValue())
                .status(product.getStatus().name())
                .options(optionDtos)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /**
     * 상품 상태 변경
     */
    @Transactional
    public ProductResponse updateProductStatus(Long id, ProductStatus status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setStatus(status);
        return getProductById(id);
    }

    /**
     * Entity to ListResponse DTO
     */
    private ProductListResponse toListResponse(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .price(product.getPrice().doubleValue())
                .status(product.getStatus().name())
                .build();
    }
}
