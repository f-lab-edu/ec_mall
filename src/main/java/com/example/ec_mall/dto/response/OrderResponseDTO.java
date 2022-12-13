package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderResponseDTO {
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO{
        private String name;
        private int price;
        private ProductSize size;
        private ProductResponseDTO.CategoryResponseDTO categoryResponseDTO;
        private ProductResponseDTO.ProductImagesResponseDTO productImagesResponseDTO;
    }
}
