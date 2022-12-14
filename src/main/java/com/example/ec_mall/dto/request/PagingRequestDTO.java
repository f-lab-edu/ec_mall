package com.example.ec_mall.dto.request;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingRequestDTO {
    private String name;
    private String imagesUrl;
    private ProductCategory bigCategory;
    private String smallCategory;
}
