package com.example.ec_mall.dto.request;

import com.example.ec_mall.dto.enums.ProductSize;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class OrderRequestDTO {
    //선택한 상품
    private long productId;

    @NotNull(message = "상품 사이즈를 선택하세요.")
    private ProductSize size;

    @NotNull(message = "수량을 입력하세요")
    private int ordersCount;
}
