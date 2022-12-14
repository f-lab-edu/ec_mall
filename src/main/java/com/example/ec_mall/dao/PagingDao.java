package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingDao {
    private long productId;                // 상품 ID
    private String name;                   // 상품명
    private String imagesUrl;              // 상품 이미지 URL
    private ProductCategory bigCategory;   // 카테고리
    private String smallCategory;          // 상세카테고리
    private int startIndex;                // DB 접근 시작 index
    private int pageSize;                  // 페이지 당 보여지는 게시글의 최대 개수
}
