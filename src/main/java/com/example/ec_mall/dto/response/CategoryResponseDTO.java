package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private long categoryId;
    private ProductCategory bigCategory;   // 카테고리
    private String smallCategory;       // 상세카테고리
}