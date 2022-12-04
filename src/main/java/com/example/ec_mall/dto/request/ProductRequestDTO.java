package com.example.ec_mall.dto.request;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    @Size(min = 0, max = 45, message = "상품명은 45자 이하로 입력 가능합니다.")
    private String name;

    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    @PositiveOrZero(message = "상품 가격은 0이상의 값만 입력 가능합니다.")
    private int price;

    @NotNull(message = "상품 사이즈는 필수 입력 값입니다.")
    private ProductSize size;

    @NotNull(message = "상품 재고는 필수 입력 값입니다.")
    @PositiveOrZero(message = "상품 재고는 0이상의 값만 입력 가능합니다.")
    private int stock;

    @NotNull(message = "상품 상세 설명은 필수 입력 값입니다.")
    @Size(min = 0, max = 100, message = "상품 상세 설명은 100자 이하로 입력 가능합니다.")
    private String info;

    @NotNull(message = "상품 이미지는 필수 입력 값입니다.")
    private String imagesUrl;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    private ProductCategory bigCategory;

    @NotNull(message = "소 카테고리는 필수 입력 값입니다.")
    private String smallCategory;
}
