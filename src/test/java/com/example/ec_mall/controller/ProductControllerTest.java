package com.example.ec_mall.controller;

import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.UpdateProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Log4j2
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    private ProductRequestDTO productRequestDTO;
    private UpdateProductRequestDTO updateProductRequestDTO;
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
                .smallCategory(categoryEnum.TOP.getLong())
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
                .smallCategory(categoryEnum.PANTS.getLong())
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
                .smallCategory(categoryEnum.TOP.getLong())
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
    @DisplayName("상품 수정 성공")
    void updateProduct() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("test")
                .price(1000)
                .stock(12)
                .size(sizeEnum.L)
                .imagesUrl("/test/img")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.PANTS.getLong())
                .info("테스트 정보")
                .build();

        doNothing().when(productService).updateProduct(updateProductRequestDTO,1L);
        mockMvc.perform(patch("/product/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequestDTO))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("상품 수정 실패 : Null 이거나 유효하지 않은 값들이 입력됐을때")
    void updateProductFail() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name(null)
                .stock(-1)
                .size(null)
                .price(-1)
                .imagesUrl(null)
                .bigCategory(null)
                .smallCategory(null)
                .info(null)
                .build();

        doNothing().when(productService).updateProduct(updateProductRequestDTO, 29L);

        mockMvc.perform(patch("/product/29").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
                .andExpect(status().isBadRequest()).andDo(print());
    }
    @Test
    @DisplayName("상품 수정 전 해당 상품 없을 시 Exception 발생 ( Status : 901, Message : 없는 상품입니다.)")
    void foundFailBeforeUpdate() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("test")
                .stock(12)
                .size(sizeEnum.S)
                .imagesUrl("/test/img")
                .bigCategory(categoryEnum.TOP)
                .smallCategory(categoryEnum.TOP.getLong())
                .info("테스트 정보")
                .build();

        doThrow(new APIException(ErrorCode.NOT_FOUND_PRODUCT)).when(productService).updateProduct(updateProductRequestDTO, 20L);

        mockMvc.perform(patch("/product/20").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
                .andExpect(result -> Assertions.assertThrows(APIException.class, () -> productService.updateProduct(updateProductRequestDTO,20L)))
                .andExpect(jsonPath("$.status").value(901))
                .andExpect(jsonPath("$.message").value("없는 상품입니다."))
                .andExpect(status().isBadRequest()).andDo(print());
    }
}