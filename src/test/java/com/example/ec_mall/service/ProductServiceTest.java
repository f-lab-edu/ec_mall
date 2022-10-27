package com.example.ec_mall.service;

import com.example.ec_mall.dto.ProductDTO;
import com.example.ec_mall.dto.ProductFormDTO;
import com.example.ec_mall.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;
    private ProductFormDTO productFormDTO;

    @BeforeEach
    public void add() {
        productFormDTO = ProductFormDTO.builder()
                .name("테스트1")
                .price(50000)
                .size("L")
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory("상의")
                .smallCategory("반팔")
                .build();
    }

    @Test
    @DisplayName("상품 등록 성공")
    public void addProductOK() throws Exception {
        productService.addProduct(productFormDTO);

        verify(productMapper).addProduct(any(ProductDTO.class));
        verify(productMapper).addProductImages(any(ProductDTO.class));
        verify(productMapper).addProductCategory(any(ProductDTO.class));
    }
}