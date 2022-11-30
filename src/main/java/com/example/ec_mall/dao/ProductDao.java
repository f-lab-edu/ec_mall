package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.Size;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductDao {
    private long productId;             // 상품ID
    private String name;                // 상품명
    private int price;                  // 상품 가격
    private Size size;              // 상품 사이즈
    private int stock;                  // 상품 재고
    private String info;                // 상품 설명
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}
