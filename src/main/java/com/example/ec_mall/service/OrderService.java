package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrders;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {
    private final OrderMapper orderMapper;

    public void order(String email, OrderRequestDTO orderRequestDTO){
        /**
         * 결제등의 추가 행위는 추후에 작업하고 현재는 product 테이블의 stock을 감소시키는 것만 한다.
         * 주문 후 재고가 0보다 작다면 exception 발생
         */
        // beforeStock : 주문 전 재고
        int beforeStock = orderMapper.findStockByProductId(orderRequestDTO.getProductId());
        // afterStock : 주문 후 재고
        int afterStock = beforeStock - orderRequestDTO.getOrdersCount();
        if(afterStock < 0) {
            log.error("주문한 수량만큼 재고가 없습니다. 주문 가능한 수량 : {}", beforeStock);
            throw new APIException(ErrorCode.NOT_ENOUGH_PRODUCT);
        }

        OrderDao orderDao = OrderDao.builder()
                .accountId(orderMapper.findAccountByEmail(email))
                .price(orderMapper.findPriceByProductId(orderRequestDTO.getProductId()))
                .createdBy(email)
                .updatedBy(email)
                .build();
        orderMapper.addOrder(orderDao);

        ProductOrders productOrders = ProductOrders.builder()
                .ordersId(orderDao.getOrderId())
                .productId(orderRequestDTO.getProductId())
                .ordersCount(orderRequestDTO.getOrdersCount())
                .createdBy(email)
                .updatedBy(email)
                .build();
        orderMapper.addProductOrder(productOrders);
        orderMapper.subtractionStock(orderRequestDTO.getProductId(), afterStock);
    }
}
