package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private ProductCategory bigCategory;   // 카테고리
    private String smallCategory;       // 상세카테고리
}
