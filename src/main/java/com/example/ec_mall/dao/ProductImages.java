package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductImages {
    private long productImagesId;       // 상품 이미지 ID
    private long productId;             // 상품 ID
    private String imagesUrl;           // 상품 이미지 URL
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}
