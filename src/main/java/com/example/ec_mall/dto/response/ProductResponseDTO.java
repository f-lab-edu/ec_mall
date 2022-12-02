package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.ProductSize;
import lombok.*;

public class ProductResponseDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImagesResponseDTO{
        private String imagesUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO{
        private long productId;
        private String name;
        private int price;
        private ProductSize size;
        private int stock;
        private String info;
        private CategoryResponseDTO categoryResponseDTO;
        private ProductImagesResponseDTO productImagesResponseDTO;
    }
}
