package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.mapper.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문성공 시 Mapper 호출 확인")
    void getProductSuccess(){
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(29L)
                .size(ProductSize.XL)
                .ordersCount(1)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);


        OrderDao orderDao = OrderDao.builder()
                .accountId(1L)
                .price(10000)
                .createdBy(loginDTO.getEmail())
                .updatedBy(loginDTO.getEmail())
                .build();

        ProductOrdersDao productOrdersDao = ProductOrdersDao.builder()
                .ordersId(orderDao.getOrderId())
                .productId(orderRequestDTO.getProductId())
                .ordersCount(orderRequestDTO.getOrdersCount())
                .createdBy(loginDTO.getEmail())
                .updatedBy(loginDTO.getEmail())
                .build();

        when(orderMapper.findStockByProductId(29L)).thenReturn(100);      // select before stock
        when(orderMapper.findPriceByProductId(29L)).thenReturn(10000);  // select price
        when(orderMapper.findAccountByEmail("test@naver.com")).thenReturn(1L);
        doNothing().when(orderMapper).addOrder(orderDao);                    // insert orderTable
        doNothing().when(orderMapper).addProductOrder(productOrdersDao);     // insert orderTable
        doNothing().when(orderMapper).subtractionStock(29L, 99);     // update after stock

        orderService.order(loginDTO.getEmail(), items);

        verify(orderMapper, times(1)).findStockByProductId(29L);
        verify(orderMapper, times(1)).findPriceByProductId(29L);
        verify(orderMapper, times(1)).findAccountByEmail("test@naver.com");
        verify(orderMapper, times(1)).addOrder(orderDao);
        verify(orderMapper, times(1)).addProductOrder(productOrdersDao);
        verify(orderMapper, times(1)).subtractionStock(29L, 99);
    }
    @Test
    @DisplayName("재고 없을시 주문 실패 : API Exception 발생")
    void OrderFail(){
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(29L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);
        when(orderMapper.findStockByProductId(29L)).thenReturn(0);
        assertThrows(APIException.class, () -> orderService.order(loginDTO.getEmail(), items));
    }

}
