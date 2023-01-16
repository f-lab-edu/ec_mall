package com.example.ec_mall.integration;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.OrderMapper;
import com.example.ec_mall.service.OrderService;
import com.example.ec_mall.util.SHA256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceIntegrationTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderService orderService;
    private LoginDTO loginDTO;

    @BeforeEach
    void login(){
        loginDTO = LoginDTO.builder()
                .email("test@test.com")
                .password(SHA256.encrypt("testPassword1!"))
                .build();
    }

    @Test
    @DisplayName("주문성공")
    void getProductSuccess(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(8L)
                .size(ProductSize.S)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        OrderDao orderDao = OrderDao.builder()
                .accountId(8L)
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

        orderService.order(loginDTO.getEmail(), items);

        verify(orderMapper, times(1)).findStockByProductId(8L);
        verify(orderMapper, times(1)).findPriceByProductId(8L);
        verify(orderMapper, times(1)).findAccountByEmail("test@test.com");
        verify(orderMapper, times(1)).addOrder(orderDao);
        verify(orderMapper, times(1)).addProductOrder(productOrdersDao);
        verify(orderMapper, times(1)).subtractionStock(8L, 1);
    }
    @Test
    @DisplayName("재고 없을시 주문 실패 : API Exception 발생")
    void OrderFail(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(8L)
                .size(ProductSize.XL)
                .ordersCount(20)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        APIException exception = assertThrows(APIException.class, () -> orderService.order(loginDTO.getEmail(), items));
        assertThat(ErrorCode.NOT_ENOUGH_PRODUCT).isEqualTo(exception.getErrorCode());
    }
}
