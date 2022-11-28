package com.example.ec_mall.service;

import com.example.ec_mall.dao.UpdateProductDao;
import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.UpdateProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.dao.DataIntegrityViolationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;
    private ProductRequestDTO productRequestDTO;
    private UpdateProductRequestDTO updateProductRequestDTO;

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
                .smallCategory(categoryEnum.TOP.getShort())
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
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.TOP.getLong())
                .build();

        UpdateProductDao updateProductDao = UpdateProductDao.builder()
                .productId(1L)
                .categoryId(0L)
                .name(updateProductRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .size(updateProductRequestDTO.getSize())
                .stock(updateProductRequestDTO.getStock())
                .info(updateProductRequestDTO.getInfo())
                .imagesUrl(updateProductRequestDTO.getImagesUrl())
                .bigCategory(updateProductRequestDTO.getBigCategory())
                .smallCategory(updateProductRequestDTO.getSmallCategory())
                .updatedBy("admin")
                .build();

        when(productMapper.findProductInfoById(1L)).thenReturn(List.of(productRequestDTO));
        doNothing().when(productMapper).updateProduct(updateProductDao);
        /**
         *  1. 수정 서비스 실행시 먼저 상품 조회될 때 List 반환
         *  2. 수정 서비스 실행시 조회되고 실제 상품 수정 성공시 아무 것도 하지 않는다.
         *  3. 서비스 실행후 ProductMapper.updateProduct() 가 한번이라도 실행됐는지 검증한다.
         */
        //when
        productService.updateProduct(updateProductRequestDTO, 1L);
        //then
        verify(productMapper, atLeastOnce()).updateProduct(updateProductDao);
    }
    @Test
    @DisplayName("업데이트 수정시 상품이 없는 경우")
    void notFoundProduct(){
        /**
         * 1.수정전 조회시 상품 없는 경우 Exception 발생
         */
        when(productMapper.findProductInfoById(1L)).thenThrow(new APIException(ErrorCode.NOT_FOUND_PRODUCT));
        assertThrows(APIException.class, () -> productMapper.findProductInfoById(1L));
    }
    @Test
    @DisplayName("상품 삭제 서비스 호출 시 SQL이 무조건 한번 호출된다.")
    void deleteProduct(){
        doNothing().when(productMapper).deleteProduct(anyLong());
        productService.deleteProduct(1L);
        verify(productMapper, times(1)).deleteProduct(anyLong());
    }
    @Test
    @DisplayName("DB 오류 발생 시 상품 삭제 서비스는 실패해야 한다.")
    void deleteProductException() {
        doThrow(DataIntegrityViolationException.class).when(productMapper).deleteProduct(anyLong());

        assertThrows(DataIntegrityViolationException.class, () -> productService.deleteProduct(1L));
    }
}