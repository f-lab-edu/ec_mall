package com.example.ec_mall.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductImagesDTO {
    private long productImagesId;       // 상품 이미지 ID
    private long productId;             // 상품 ID
    private String imagesUrl;           // 상품 이미지 URL
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}