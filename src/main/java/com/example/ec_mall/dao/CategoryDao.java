package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.categoryEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDao {
    private Long categoryId;
    private categoryEnum bigCategory;
    private String smallCategory;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
}
