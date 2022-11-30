package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.Category;
import com.example.ec_mall.dto.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDao {
    private Long productId;
    private Long categoryId;
    private String name;
    private int price;
    private Size size;
    private int stock;
    private String info;
    private String imagesUrl;
    private Category bigCategory;
    private String smallCategory;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
