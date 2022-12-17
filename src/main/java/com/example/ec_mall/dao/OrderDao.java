package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDao {
    private long orderId;               // 주문 ID
    private long accountId;             // 계정 ID
    private int price;                  // 주문가격
    private LocalDateTime orderDate;    // 주문일자
    private String createdBy;           // 등록자
    private LocalDateTime createdDate;  // 등록일자
    private String updatedBy;           // 수정자
    private LocalDateTime updatedDate;  // 수정일자
}
