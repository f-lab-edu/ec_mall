package com.example.ec_mall.service;

import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.Top)
                .smallCategory("반팔")
                .build();
    }
    @Test
    @DisplayName("상품 등록 서비스 호출 시 3개의 SQL이 무조건 한번 호출된다.")
    void addProduct(){
        doNothing().when(productMapper).addProduct(any());
        doNothing().when(productMapper).addProductImages(any());
        doNothing().when(productMapper).addProductCategory(any());

        productService.addProduct(productRequestDTO);

        verify(productMapper, times(1)).addProduct(any());
        verify(productMapper, times(1)).addProductImages(any());
        verify(productMapper, times(1)).addProductCategory(any());
    }
}