package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDao {
    private long categoryId;            // 카테고리 ID
    private ProductCategory bigCategory;   // 카테고리
    private String smallCategory;       // 상세카테고리
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}