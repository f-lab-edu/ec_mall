package com.example.ec_mall.controller;

import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    /*
        @Mock => Mockito.mock()를 줄인 것이며, 가짜 객체를 만들어 사용하는 것을 말한다.
        @MockBean => mock 객체를 스프링 컨텍스트에 등록하고 @Autowired로 스프링 컨텍스트에 등록된 mock 객체들을 주입받아서 의존성 처리를 해준다.
        @InjectMocks => @InjectMocks는 의존성이 필요로 하는 필드에 붙이는 어노테이션으로 @Mock이나 @Spy 어노테이션이 붙은 필드를 주입받는다.
     */
    @MockBean
    private ProductService productService;
    private ProductRequestDTO productRequestDTO;
    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.M)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.TOP.getShort())
                .build();
    }
    @Test
    @DisplayName("상품 등록 성공")
    void addProduct() throws Exception {
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO))).andDo(print()).andExpect(status().isCreated());
    }

    /*
        @NotNull : Null만 허용하지 않는다. “”(초기화된 스트링) 이나 “ “(빈 공백 문자열)은 허용
        @NotBlank : 전부 허용하지 않는다.
     */
    @Test
    @DisplayName("상품명에 null, 공백이 들어갈 경우 실패해야한다.")
    void addProductNotBlank() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name(null)
                .price(50000)
                .size(sizeEnum.M)
                .stock(150)
                .info("상품 상세 설명입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.PANTS)
                .smallCategory(categoryEnum.PANTS.getShort())
                .build();

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO))).andDo(print());
    }
    @Test
    @DisplayName("사이즈, 상품 상세 설명, 이미지URL, 카테고리, 소카테고리에 null이 들어갈 경우 실패해야한다.")
    void addProductNotNull() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("상품명")
                .price(50000)
                .size(null)
                .stock(150)
                .info(" ")
                .imagesUrl(null)
                .bigCategory(null)
                .smallCategory(null)
                .build();

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO))).andDo(print());
    }
    @Test
    @DisplayName("가격과 재고에 음수 값이 입력되면 실패해야한다.")
    void addProductPOZ() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("테스트")
                .price(-50000)
                .size(sizeEnum.M)
                .stock(-150)
                .info("상품 상세 설명 테스트입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.TOP.getShort())
                .build();

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO))).andDo(print());
    }
    @Test
    @DisplayName("상품명과 상품 상세 설명의 글자수가 45자, 100자가 넘어갈 경우 실패해야한다.")
    void addProductSize() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.")
                .price(32000)
                .size(sizeEnum.M)
                .stock(150)
                .info("상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.TOP.getLong())
                .build();

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO))).andDo(print());
    }
    @Test
    @DisplayName("관리자가 상품 삭제를 성공한다.")
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/product/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("productId가 잘 못된 경우 상품 삭제를 실패해야한다.")
    void deleteProductError() throws Exception{
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/product/delete/TEST"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}