package com.example.ec_mall.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductMapperTest {
    @Autowired
    ProductMapper productMapper;

    @Test
    @DisplayName("상품 데이터")
    public void productAllTest(){
        productMapper.product();
    }
}