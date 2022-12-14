package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dao.ProductOrdersDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void addOrder(OrderDao orderDao);
    void addProductOrder(ProductOrdersDao productOrdersDao);
    long findAccountByEmail(String Email);
    int findPriceByProductId(long id);
    int findStockByProductId(long id);
    void subtractionStock(long id, int stock);
}
