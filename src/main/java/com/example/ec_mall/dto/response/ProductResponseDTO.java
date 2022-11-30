package com.example.ec_mall.dto.response;

import com.example.ec_mall.dto.enums.sizeEnum;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private long productId;
    private String name;
    private int price;
    private sizeEnum size;
    private int stock;
    private String info;
    private CategoryResponseDTO categoryResponseDTO;
    private ProductImagesResponseDTO productImagesResponseDTO;
}
