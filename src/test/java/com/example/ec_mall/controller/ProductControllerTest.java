package com.example.ec_mall.controller;

import com.example.ec_mall.dto.ProductFormDTO;
import com.example.ec_mall.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private ProductFormDTO productFormDTO;

    @BeforeEach
    public void add() {
        productFormDTO = ProductFormDTO.builder()
                .name("테스트1")
                .price(50000)
                .size("L")
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory("상의")
                .smallCategory("반팔")
                .build();
    }

    @Test
    @DisplayName("상품 등록 성공")
    public void addProductOK() throws Exception {
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productFormDTO))).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품명 Null_등록 실패")
    public void addProductNameNull() throws Exception{
        ProductFormDTO productFormDTO = ProductFormDTO.builder()
                .price(50000)
                .size("L")
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory("상의")
                .smallCategory("반팔")
                .build();

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productFormDTO))).andDo(print());

    }
}