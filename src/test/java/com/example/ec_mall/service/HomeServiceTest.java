package com.example.ec_mall.service;

import com.example.ec_mall.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private HomeService homeService;

    @Test
    @DisplayName("상품 목록(페이징) 호출 시 SQL 한번 호출되어야 한다.")
    void productPage(){
        when(productMapper.productPage(0, 20)).thenReturn(List.of());
        when(productMapper.productPageCount()).thenReturn(24);
        homeService.home(1);
        verify(productMapper, times(1)).productPage(0, 20);
        verify(productMapper, times(1)).productPageCount();
    }
    @Test
    @DisplayName("DB 오류 발생 시 상품 목록(페이징) 서비스는 실패해야 한다.")
    void productPageException(){
        doThrow(DataIntegrityViolationException.class).when(productMapper).productPage(0,20);
        assertThrows(DataIntegrityViolationException.class, () -> homeService.home(1));
    }
}