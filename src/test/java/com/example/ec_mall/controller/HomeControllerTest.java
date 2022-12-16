package com.example.ec_mall.controller;

import com.example.ec_mall.paging.PagingUtil.*;
import com.example.ec_mall.service.HomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HomeService homeService;

    @Test
    @DisplayName("상품 목록 조회(페이징)를 성공한다.")
    void productPage() throws Exception{
        Paging pageUtil = Paging.builder()
                .name("test")
                .productImages(new ProductImages("/test/test.jpg"))
                .build();

        when(homeService.home(0, 20)).thenReturn(List.of(pageUtil));
        mockMvc.perform(get("/home/app")
               .param("page", "1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(pageUtil)))
               .andExpect(status().isOk())
               .andDo(print());
    }
}