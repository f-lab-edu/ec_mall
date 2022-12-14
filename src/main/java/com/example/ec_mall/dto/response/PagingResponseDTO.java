package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PagingResponseDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImagesResponseDTO{
        private String imagesUrl;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryResponseDTO{
        private ProductCategory bigCategory;
        private String smallCategory;
    }

    @Data
    @Builder
    public static class ResponseDTO{
        private long productId;
        private String name;
        private CategoryResponseDTO categoryResponseDTO;
        private ProductImagesResponseDTO productImagesResponseDTO;
    }
}
