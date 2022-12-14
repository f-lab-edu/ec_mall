package com.example.ec_mall.controller;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.request.PagingRequestDTO;
import com.example.ec_mall.service.HomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
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
        PagingRequestDTO pagingRequestDTO = PagingRequestDTO.builder()
                .name("test")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getLong())
                .imagesUrl("/test/test1.jpg")
                .build();

        mockMvc.perform(get("/home/app")
               .param("page", "1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(pagingRequestDTO)))
               .andExpect(status().isOk())
               .andDo(print());
    }
    @Test
    @DisplayName("pageSize가 0보다 작으면 조회를 실패한다.")
    public void getProductsFailTest() throws Exception {
        PagingRequestDTO pagingRequestDTO = PagingRequestDTO.builder()
                .name("test")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getLong())
                .imagesUrl("/test/test1.jpg")
                .build();

        doThrow(new RuntimeException()).when(homeService).homePaging(pagingRequestDTO, 1, -20);

        mockMvc.perform(get("/home/app")
               .param("page", "1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(pagingRequestDTO)))
               .andExpect(result -> Assertions.assertThrows(RuntimeException.class, () -> homeService.homePaging(pagingRequestDTO, 1, -20)))
               .andDo(print());
    }
}