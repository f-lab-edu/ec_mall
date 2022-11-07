package com.example.ec_mall.controller;

import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

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
    @Autowired
    private Validator validator;

    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(sizeEnum.M)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.상의)
                .smallCategory("반팔")
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
    @DisplayName("NotBlank,NotNull_등록 실패")
    void addProductNotNull() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name(null)
                .price(50000)
                .size(null)
                .stock(150)
                .info(" ")
                .imagesUrl(null)
                .bigCategory(null)
                .smallCategory(null)
                .build();

        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(productRequestDTO);

        for (ConstraintViolation<ProductRequestDTO> violation : violations) {
            log.error(violation.getMessage());
        }
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO)))
                        .andExpect(jsonPath("$.status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage())).andDo(print());
    }
    @Test
    @DisplayName("PositiveOrZero,Size_등록 실패")
    void addProductSize() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.")
                .price(-50000)
                .size(sizeEnum.M)
                .stock(-150)
                .info("상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(categoryEnum.상의)
                .smallCategory("긴팔")
                .build();

        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(productRequestDTO);

        for (ConstraintViolation<ProductRequestDTO> violation : violations) {
            log.error(violation.getMessage());
        }
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO)))
                        .andExpect(jsonPath("$.status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage())).andDo(print());
    }
}