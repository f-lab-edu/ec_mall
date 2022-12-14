package com.example.ec_mall.service;

import com.example.ec_mall.dao.PagingDao;
import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.request.PagingRequestDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private HomeService homeService;
    private PagingRequestDTO pagingRequestDTO;

    @BeforeEach
    void init() {
        pagingRequestDTO = PagingRequestDTO.builder()
                .name("테스트1")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .build();
    }
    @Test
    @DisplayName("상품 목록(페이징) 호출 시 SQL 한번 호출되어야 한다.")
    void productPage(){
        PagingDao pagingDao = PagingDao.builder()
                .name("테스트1")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .startIndex(0)
                .pageSize(20)
                .build();

        when(productMapper.productPageCount()).thenReturn(24);
        homeService.productPageCount();
        verify(productMapper, times(1)).productPageCount();

        when(productMapper.productPage(pagingDao)).thenReturn(List.of());
        homeService.homePaging(pagingRequestDTO, 0, 20);
        verify(productMapper, times(1)).productPage(pagingDao);
    }
    @Test
    @DisplayName("DB 오류 발생 시 상품 목록(페이징) 서비스는 실패해야 한다.")
    void productPageException(){
        PagingDao pagingDao = PagingDao.builder()
                .name("테스트1")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .startIndex(0)
                .pageSize(20)
                .build();

        doThrow(DataIntegrityViolationException.class).when(productMapper).productPage(pagingDao);
        assertThrows(DataIntegrityViolationException.class, () -> homeService.homePaging(pagingRequestDTO, 0, 20));
    }
}