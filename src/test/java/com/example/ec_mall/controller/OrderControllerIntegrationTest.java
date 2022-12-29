package com.example.ec_mall.controller;

import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.OrderService;
import com.example.ec_mall.service.ProductService;
import com.example.ec_mall.util.SHA256;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrderService orderService;
    @MockBean
    ProductService productService;
    OrderRequestDTO orderRequestDTO;
    LoginDTO loginDTO;
    MockHttpSession loginSession;

    @BeforeEach
    void login(){
        loginDTO = LoginDTO.builder()
                .email("est@test.com")
                .password(SHA256.encrypt("testPassword1!"))
                .build();

        loginSession = new MockHttpSession();
        loginSession.setAttribute("account", loginDTO.getEmail());
    }
    @Test
    @DisplayName("주문 성공")
    void orderSuccess() throws Exception{
        orderRequestDTO = OrderRequestDTO.builder()
                .productId(8L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        mockMvc.perform(post("/order")
               .session(loginSession)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(items)))
               .andExpect(status().isOk())
               .andDo(print());
    }
    @Test
    @DisplayName("재고가 없을시 주문 실패: status : 910, message : 재고를 확인해주세요")
    void orderFail() throws Exception{
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.XL)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);
        doThrow(new APIException(ErrorCode.NOT_ENOUGH_PRODUCT)).when(orderService).order(loginSession.getAttribute("account").toString(), items);
        mockMvc.perform(post("/order")
               .session(loginSession)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(items)))
               .andExpect(result -> Assertions.assertThrows(APIException.class, () -> orderService.order(loginSession.getAttribute("account").toString(), items)))
               .andExpect(jsonPath("$.status").value(910))
               .andExpect(jsonPath("$.message").value("재고를 확인해주세요"))
               .andDo(print());
    }
}
