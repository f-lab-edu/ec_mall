package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.OrderMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Log4j2
public class OrderServiceIntegrationTest {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderService orderService;
    LoginDTO loginDTO;

    @BeforeEach
    void login(){
        loginDTO = LoginDTO.builder()
                .email("est@test.com")
                .password("testPassword1!")
                .build();
    }

    @Test
    @DisplayName("주문성공")
    void getProductSuccess(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(8L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        int totalPrice = 0;
        for (OrderRequestDTO item : items) {
            // beforeStock : 주문 전 재고
            int beforeStock = orderMapper.findStockByProductId(item.getProductId());
            // afterStock : 주문 후 재고
            int afterStock = beforeStock - item.getOrdersCount();
            if (afterStock < 0) {
                log.error("주문한 수량만큼 재고가 없습니다. 주문 가능한 수량 : {}", beforeStock);
                throw new APIException(ErrorCode.NOT_ENOUGH_PRODUCT);
            }
            totalPrice += orderMapper.findPriceByProductId(item.getProductId());

            //재고 차감
            orderMapper.subtractionStock(item.getProductId(), afterStock);
        }
        OrderDao orderDao = OrderDao.builder()
                .accountId(orderMapper.findAccountByEmail(loginDTO.getEmail()))
                //주문 가격 : 주문상품의 개당 가격 * 주문 수량
                .price(totalPrice)
                .createdBy(loginDTO.getEmail())
                .updatedBy(loginDTO.getEmail())
                .build();
        orderMapper.addOrder(orderDao);

        for (OrderRequestDTO item : items) {
            ProductOrdersDao productOrdersDao = ProductOrdersDao.builder()
                    .ordersId(orderDao.getOrderId())
                    .productId(item.getProductId())
                    .createdBy(loginDTO.getEmail())
                    .updatedBy(loginDTO.getEmail())
                    .build();
            orderMapper.addProductOrder(productOrdersDao);
        }
    }
    @Test
    @DisplayName("재고 없을시 주문 실패 : API Exception 발생")
    void OrderFail(){
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(8L)
                .size(ProductSize.XL)
                .ordersCount(5)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        APIException exception = assertThrows(APIException.class, () -> orderService.order(loginDTO.getEmail(), items));
        assertEquals(ErrorCode.NOT_ENOUGH_PRODUCT, exception.getErrorCode());
    }
}
