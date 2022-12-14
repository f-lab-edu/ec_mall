package com.example.ec_mall.controller;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO;
import com.example.ec_mall.service.MemberService;
import com.example.ec_mall.service.OrderService;
import com.example.ec_mall.util.SHA256;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderController {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    private MockHttpSession session;

    @BeforeEach
    void login(){
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        session = new MockHttpSession();
        session.setAttribute("account", loginDTO.getEmail());
    }
//    @BeforeEach
//    void product(){
//        ProductResponseDTO.ResponseDTO responseDTO = ProductResponseDTO.ResponseDTO.builder()
//                .productId(1L)
//                .categoryResponseDTO(new ProductResponseDTO.CategoryResponseDTO(ProductCategory.PANTS, "Short-Pants"))
//                .productImagesResponseDTO(new ProductResponseDTO.ProductImagesResponseDTO("23f23f"))
//                .build();
//    }

    @Test
    @DisplayName("주문 성공")
    void orderSuccess() throws Exception{
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();

        doNothing().when(orderService).order(session.toString(), orderRequestDTO);
        mockMvc.perform(post("/order/orderShee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(orderRequestDTO)))
                .andExpect(status().isOk()).andDo(print());
    }
}
