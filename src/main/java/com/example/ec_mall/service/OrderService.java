package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderService {
    private final OrderMapper orderMapper;

    public void order(String email, OrderRequestDTO orderRequestDTO) {
        /**
         * 결제등의 추가 행위는 추후에 작업하고 현재는 product 테이블의 stock을 감소시키는 것만 한다.
         * 주문 후 재고가 0보다 작다면 exception 발생
         */
        int totalPrice = 0;
        for (int i = 0; i < orderRequestDTO.getItems().size(); i++) {
            // beforeStock : 주문 전 재고
            int beforeStock = orderMapper.findStockByProductId(orderRequestDTO.getItems().get(i).getProductId());
            // afterStock : 주문 후 재고
            int afterStock = beforeStock - orderRequestDTO.getItems().get(i).getOrdersCount();
            if (afterStock < 0) {
                log.error("주문한 수량만큼 재고가 없습니다. 주문 가능한 수량 : {}", beforeStock);
                throw new APIException(ErrorCode.NOT_ENOUGH_PRODUCT);
            }
            totalPrice += orderMapper.findPriceByProductId(orderRequestDTO.getItems().get(i).getProductId());
            afterStock = 0;


            OrderDao orderDao = OrderDao.builder()
                    .accountId(orderMapper.findAccountByEmail(email))
                    //주문 가격 : 주문상품의 개당 가격 * 주문 수량
                    .price(totalPrice)
                    .createdBy(email)
                    .updatedBy(email)
                    .build();
            orderMapper.addOrder(orderDao);

            ProductOrdersDao productOrdersDao = ProductOrdersDao.builder()
                    .ordersId(orderDao.getOrderId())
                    .productId(orderRequestDTO.getItems().get(i).getProductId())
                    .ordersCount(orderRequestDTO.getOrdersCount())
                    .createdBy(email)
                    .updatedBy(email)
                    .build();
            orderMapper.addProductOrder(productOrdersDao);
            orderMapper.subtractionStock(orderRequestDTO.getItems().get(i).getProductId(), afterStock);
        }
    }

}
