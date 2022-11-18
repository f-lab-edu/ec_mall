package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDao {
    private Long categoryId;
    private String bigCategory;
    private String smallCategory;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
}
