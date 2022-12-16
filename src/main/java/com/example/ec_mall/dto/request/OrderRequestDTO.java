package com.example.ec_mall.dto.request;

import com.example.ec_mall.dto.enums.ProductSize;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class OrderRequestDTO {
    //선택한 상품
    private long productId;

    private ProductSize size;

    private int ordersCount;

    private List<OrderRequestDTO> items;
}
