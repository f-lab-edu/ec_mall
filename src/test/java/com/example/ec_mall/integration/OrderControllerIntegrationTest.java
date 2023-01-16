package com.example.ec_mall.integration;

import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("주문 성공")
    void orderSuccess() throws Exception{
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.S)
                .ordersCount(1)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(items)))
               .andExpect(status().isOk())
               .andDo(print());
    }
    @Test
    @WithMockUser
    @DisplayName("재고가 없을시 주문 실패: status : 910, message : 재고를 확인해주세요")
    void orderFail() throws Exception{
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .productId(1L)
                .size(ProductSize.S)
                .ordersCount(2)
                .build();
        List<OrderRequestDTO> items = List.of(orderRequestDTO);

        mockMvc.perform(post("/order")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(items)))
               .andExpect(jsonPath("$.status").value(910))
               .andExpect(jsonPath("$.message").value("재고를 확인해주세요"))
               .andDo(print());
    }
}
