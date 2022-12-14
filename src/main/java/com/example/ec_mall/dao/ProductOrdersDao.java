package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductOrdersDao {
    private long productOrdersId;       // product_orders ID
    private long ordersId;              // orders ID
    private long productId;             // product ID
    private int ordersCount;            // 주문 수량
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}
