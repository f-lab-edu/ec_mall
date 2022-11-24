package com.example.ec_mall.service;

import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.UpdateProductDao;
import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.mapper.ProductMapper;
import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;
    private ProductRequestDTO productRequestDTO;
    private UpdateProductDao updateProductDao;

    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.JEAN)
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

    @Test
    @DisplayName("상품 수정 서비스 호출 시 SQL이 한번 호출된다.")
    void updateSuccess(){
        //given
        UpdateProductDao update = UpdateProductDao.builder()
                .productId(1L)
                .categoryId(0L)
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.T_SHIRT)
                .updatedBy("admin")
                .build();

        //when
        productService.updateProduct(productRequestDTO, 1L);

        //then
        verify(productMapper, atLeastOnce()).updateProduct(update);
    }

    @Test
    @DisplayName("상품 수정 성공시 변경 전 값과 비교.")
    void updateExpectedName(){
        ProductRequestDTO update = ProductRequestDTO.builder()
                .name("test1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.JEAN)
                .build();

        when(productMapper.findProductInfoById(2L)).thenReturn(List.of(update));
        List<ProductRequestDTO> findById = productService.getProductInfo(2L);
        assertNotEquals(findById.get(0).getName(), productRequestDTO.getName());
    }

    @Test
    @DisplayName("SQL 혹은 Data 에러시 수정 실패")
    void updateFail(){

    }

    @Test
    @DisplayName("업데이트 상품 조회시 값이 없는 경우")
    void notFoundCategoryId(){
        when(productMapper.findCategoryId(1L)).thenThrow(BindingException.class);
        assertThrows(BindingException.class, ()-> productService.updateProduct(productRequestDTO, 1L));
    }
}