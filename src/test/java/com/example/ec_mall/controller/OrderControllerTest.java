package com.example.ec_mall.controller;

import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;


    @Test
    @DisplayName("주문 성공")
    void orderSuccess() throws Exception{
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);
        doNothing().when(orderService).order(loginDTO.getEmail(), items);
        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(orderRequestDTO)))
                .andExpect(status().isOk()).andDo(print());
    }
    @Test
    @DisplayName("재고가 없을시 주문 실패: status : 910, message : 재고를 확인해주세요")
    void orderFail() throws Exception{
        LoginDTO loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);
        doThrow(new APIException(ErrorCode.NOT_ENOUGH_PRODUCT)).when(orderService).order(loginDTO.getEmail(), items);
        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(orderRequestDTO)))
                .andExpect(result -> Assertions.assertThrows(APIException.class, () -> orderService.order(loginDTO.getEmail(), items)))
                .andExpect(jsonPath("$.status").value(910))
                .andExpect(jsonPath("$.message").value("재고를 확인해주세요"))
                .andDo(print());
    }
}
