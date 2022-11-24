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
    private UpdateProductRequestDTO updateProductRequestDTO;
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
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.T_SHIRT)
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

        //when
        productService.updateProduct(updateProductRequestDTO, 1L);

        //then
        verify(productMapper, atLeastOnce()).updateProduct(updateProductDao);
    }

    @Test
    @DisplayName("상품 수정 변경 전 값과 비교.")
    void updateExpectedName(){
        ProductRequestDTO before = ProductRequestDTO.builder()
                .name("test1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.JEAN)
                .build();

        UpdateProductDao after = UpdateProductDao.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.JEAN)
                .build();

        when(productMapper.findProductInfoById(2L)).thenReturn(List.of(before));
        List<ProductRequestDTO> findById = productService.getProductInfo(2L);
        assertNotEquals(after.getName(), findById.get(0).getName());
    }

    @Test
    @DisplayName("SQL 혹은 Data 에러시 수정 실패")
    void updateFail(){
        UpdateProductRequestDTO update = UpdateProductRequestDTO.builder()
                .name("test1")
                .price(12000)
                .stock(12)
                .size(sizeEnum.L)
                .imagesUrl("/test/img")
                .bigCategory(categoryEnum.PANTS)
                .smallCategory(categoryEnum.JEAN)
                .info("테스트 정보")
                .build();
        doThrow(DataIntegrityViolationException.class).when(productMapper).updateProduct(updateProductDao);
        assertThrows(DataIntegrityViolationException.class, () -> productService.updateProduct(update, 1L));
    }

    @Test
    @DisplayName("업데이트 수정시 상품이 없는 경우")
    void notFoundProduct(){
        UpdateProductRequestDTO updateProduct = UpdateProductRequestDTO.builder()
                .name("test1")
                .price(50000)
                .size(sizeEnum.S)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.JEAN)
                .build();

        when(productMapper.findProductInfoById(1L)).thenThrow(new APIException(ErrorCode.NOT_FOUND_PRODUCT));
        assertThrows(APIException.class, () -> productService.updateProduct(updateProduct, 1L));
    }
}