package com.example.ec_mall.service;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO.*;
import com.example.ec_mall.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {
    @Autowired
    ProductService productService;
    @Autowired
    ProductMapper productMapper;

    @Test
    @DisplayName("상품 등록 성공")
    void addProduct(){
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(ProductSize.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .build();
        productService.addProduct(productRequestDTO);
    }
    @Test
    @DisplayName("상품 수정 성공")
    void updateSuccess(){
        UpdateProductRequestDTO updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("테스트11111")
                .price(50000)
                .size(ProductSize.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getLong())
                .build();
        productService.updateProduct(updateProductRequestDTO, 1L);
        assertThat(updateProductRequestDTO.getName()).isEqualTo("테스트11111");
    }
    @Test
    @DisplayName("상품 조회 성공")
    void getProductSuccess(){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .productId(8L)
                .name("테스트1")
                .price(50000)
                .info("상품 상세 설명입니다!")
                .size(ProductSize.S)
                .stock(30)
                .categoryResponseDTO(new CategoryResponseDTO(ProductCategory.TOP, ProductCategory.TOP.getLong()))
                .productImagesResponseDTO(new ProductImagesResponseDTO("/product/images/test1.jpg"))
                .build();
        productService.getProduct(8L);
        assertThat(responseDTO.getProductId()).isNotNull();
        assertThat(responseDTO.getName()).isNotNull();
    }
    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct(){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .productId(8L)
                .name("테스트1")
                .price(50000)
                .info("상품 상세 설명입니다!")
                .size(ProductSize.S)
                .stock(30)
                .categoryResponseDTO(new CategoryResponseDTO(ProductCategory.TOP, ProductCategory.TOP.getLong()))
                .productImagesResponseDTO(new ProductImagesResponseDTO("/product/images/test1.jpg"))
                .build();
        productService.deleteProduct(responseDTO.getProductId());
    }
}