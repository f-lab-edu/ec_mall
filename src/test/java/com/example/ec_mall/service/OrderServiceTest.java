package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderService orderService;
    private MockHttpSession loginSession;

    @BeforeEach
    void login(){
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        loginSession = new MockHttpSession();
        loginSession.setAttribute("account", loginDTO.getEmail());

    }

    @Test
    @DisplayName("주문성공 시 Mapper 호출 확인")
    void getProductSuccess(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(29L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();


        OrderDao orderDao = OrderDao.builder()
                .accountId(1L)
                .price(10000)
                .createdBy(loginSession.getAttribute("account").toString())
                .updatedBy(loginSession.getAttribute("account").toString())
                .build();

        ProductOrdersDao productOrdersDao = ProductOrdersDao.builder()
                .ordersId(orderDao.getOrderId())
                .productId(orderRequestDTO.getProductId())
                .ordersCount(orderRequestDTO.getOrdersCount())
                .createdBy(loginSession.getAttribute("account").toString())
                .updatedBy(loginSession.getAttribute("account").toString())
                .build();

        when(orderMapper.findStockByProductId(29L)).thenReturn(3);      // select before stock
        when(orderMapper.findPriceByProductId(29L)).thenReturn(10000);  // select price
        when(orderMapper.findAccountByEmail("test@naver.com")).thenReturn(1L);
        doNothing().when(orderMapper).addOrder(orderDao);                    // insert orderTable
        doNothing().when(orderMapper).addProductOrder(productOrdersDao);     // insert orderTable
        doNothing().when(orderMapper).subtractionStock(29L, 1);     // update after stock

        orderService.order(loginSession.getAttribute("account").toString(),orderRequestDTO);

        verify(orderMapper, times(1)).findStockByProductId(29L);
        verify(orderMapper, times(1)).findPriceByProductId(29L);
        verify(orderMapper, times(1)).findAccountByEmail("test@naver.com");
        verify(orderMapper, times(1)).addOrder(orderDao);
        verify(orderMapper, times(1)).addProductOrder(productOrdersDao);
        verify(orderMapper, times(1)).subtractionStock(29L, 1);
    }
    @Test
    @DisplayName("재고 없을시 주문 실패 : API Exception 발생")
    void OrderFail(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(29L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();

        when(orderMapper.findStockByProductId(29L)).thenReturn(0);
        assertThrows(APIException.class, () -> orderService.order(loginSession.getAttribute("account").toString(), orderRequestDTO));
    }

}
