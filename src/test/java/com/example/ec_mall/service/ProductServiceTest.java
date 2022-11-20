package com.example.ec_mall.service;

import com.example.ec_mall.dao.UpdateProductDao;
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
import org.springframework.dao.DataIntegrityViolationException;
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

    @Test
    @DisplayName("상품 수정 서비스 호출 시 SQL이 한번 호출된다.")
    void updateSuccess(){
        //given
        UpdateProductDao update = UpdateProductDao.builder()
                .productId(1L)
                .categoryId(2L)
                .name("test1")
                .price(1000)
                .stock(12)
                .size("X")
                .imagesUrl("/test/img")
                .bigCategory("Top")
                .smallCategory("T-shirts")
                .info("테스트 정보")
                .updatedBy("admin")
                .build();

        doNothing().when(productMapper).updateProduct(any());

        //when
        productService.updateProduct(update, update.getProductId());

        //then
        verify(productMapper, atLeastOnce()).updateProduct(any());
    }

    @Test
    @DisplayName("상품 수정 성공시 변경 전 값과 비교.")
    void updateExpectedName(){
        //given
        UpdateProductDao update = UpdateProductDao.builder()
                .productId(1L)
                .categoryId(2L)
                .name("test1")
                .price(12000)
                .stock(12)
                .size("X")
                .imagesUrl("/test/img")
                .bigCategory("Top")
                .smallCategory("T-shirts")
                .info("테스트 정보")
                .updatedBy("admin")
                .build();

        //when
        String result = update.getName();
        boolean isDiffer = result.equals(productRequestDTO.getName());

        //then
        assertFalse(isDiffer);
    }
    @Test
    @DisplayName("SQL 혹은 Data 에러시 수정 실패")
    void rollback(){

        //given
        updateProductDao = UpdateProductDao.builder()
                .productId(1L)
                .categoryId(2L)
                .name("test")
                .price(1000)
                .stock(12)
                .size("X")
                .imagesUrl("/test/img")
                .bigCategory("Top")
                .smallCategory("T-shirts")
                .info("테스트 정보")
                .updatedBy("admin")
                .build();

        //when
        doThrow(DataIntegrityViolationException.class).when(productMapper).updateProduct(any());

        //then
        assertThrows(DataIntegrityViolationException.class, () -> productService.updateProduct(updateProductDao, 0L));
    }
}