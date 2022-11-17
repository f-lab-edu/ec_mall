package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateProductDao {
    private Long productId;
    private String name;
    private int price;
    private String size;
    private int stock;
    private String info;
    private String imagesUrl;
    private String bigCategory;
    private String smallCategory;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
