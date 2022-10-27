package com.example.ec_mall.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFormDTO {
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private int price;

    @NotNull(message = "상품 사이즈는 필수 입력 값입니다.")
    private String size;

    @NotNull(message = "상품 재고는 필수 입력 값입니다.")
    private int stock;

    @NotNull(message = "상품명은 필수 입력 값입니다.")
    private String info;

    @NotNull(message = "상품 이미지는 필수 입력 값입니다.")
    private String imagesUrl;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    private String bigCategory;

    @NotNull(message = "소 카테고리는 필수 입력 값입니다.")
    private String smallCategory;
}
