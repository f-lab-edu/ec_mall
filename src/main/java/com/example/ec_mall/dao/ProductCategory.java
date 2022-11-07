package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductCategory {
    private long productCategoryId;     // 상품 카테고리 ID
    private long productId;             // 상품 ID
    private long categoryId;            // 카테고리 ID
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}
