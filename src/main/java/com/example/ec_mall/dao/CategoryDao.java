package com.example.ec_mall.dao;

import com.example.ec_mall.dto.enums.categoryEnum;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDao {
    private long categoryId;
    private categoryEnum bigCategory;
    private String smallCategory;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
