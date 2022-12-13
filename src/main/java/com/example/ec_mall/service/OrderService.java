package com.example.ec_mall.service;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrders;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    public void order(String email, OrderRequestDTO orderRequestDTO, long id){

        OrderDao orderDao = OrderDao.builder()
                .accountId(orderMapper.findAccountByEmail(email))
                .price(orderMapper.findPriceByProductId(id))
                .createdBy(email)
                .updatedBy(email)
                .build();
        orderMapper.addOrder(orderDao);

        ProductOrders productOrders = ProductOrders.builder()
                .ordersId(orderDao.getOrderId())
                .productId(id)
                .ordersCount(orderRequestDTO.getOrdersCount())
                .createdBy(email)
                .updatedBy(email)
                .build();
        orderMapper.addProductOrder(productOrders);

        /**
         * 결제등의 추가 행위는 추후에 작업하고 현재는 product 테이블의 stock을 감소시키는 것만 한다.
         */
        // beforeStock : 주문 전 재고
        int beforeStock = orderMapper.findStockByProductId(id);
        // afterStock : 주문 후 재고
        int afterStock = beforeStock - orderRequestDTO.getOrdersCount();
        if(beforeStock - afterStock > 0) {
            orderMapper.subtractionStock(id, afterStock);
        }
    }
}
