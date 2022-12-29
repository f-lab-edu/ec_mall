package com.example.ec_mall.controller;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.ProductService;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProductService productService;
    ProductRequestDTO productRequestDTO;
    UpdateProductRequestDTO updateProductRequestDTO;

    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트1")
                .price(50000)
                .size(ProductSize.M)
                .stock(30)
                .info("상품 상세 설명입니다!")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .build();
    }
    @Test
    @DisplayName("상품 등록 성공")
    void addProduct() throws Exception {
        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andDo(print())
               .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("상품명에 null, 공백이 들어갈 경우 실패해야한다.")
    void addProductNotBlank() throws Exception{
        productRequestDTO = ProductRequestDTO.builder()
                .name(null)
                .price(50000)
                .size(ProductSize.M)
                .stock(150)
                .info("상품 상세 설명입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.PANTS)
                .smallCategory(ProductCategory.PANTS.getShort())
                .build();

        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("name:상품명은 필수 입력 값입니다.")))
               .andDo(print());
    }
    @Test
    @DisplayName("사이즈, 상품 상세 설명, 이미지URL, 카테고리, 소카테고리에 null이 들어갈 경우 실패해야한다.")
    void addProductNotNull() throws Exception{
        productRequestDTO = ProductRequestDTO.builder()
                .name("상품명")
                .price(50000)
                .size(null)
                .stock(150)
                .info(null)
                .imagesUrl(null)
                .bigCategory(null)
                .smallCategory(null)
                .build();

        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("bigCategory:카테고리는 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("smallCategory:소 카테고리는 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("imagesUrl:상품 이미지는 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("info:상품 상세 설명은 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("size:상품 사이즈는 필수 입력 값입니다.")))
               .andDo(print());
    }
    @Test
    @DisplayName("가격과 재고에 음수 값이 입력되면 실패해야한다.")
    void addProductPOZ() throws Exception{
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트")
                .price(-50000)
                .size(ProductSize.M)
                .stock(-150)
                .info("상품 상세 설명 테스트입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getShort())
                .build();

        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("stock:상품 재고는 0이상의 값만 입력 가능합니다.")))
               .andExpect(content().string(containsString("price:상품 가격은 0이상의 값만 입력 가능합니다.")))
               .andDo(print());
    }
    @Test
    @DisplayName("상품명과 상품 상세 설명의 글자수가 45자, 100자가 넘어갈 경우 실패해야한다.")
    void addProductSize() throws Exception{
        productRequestDTO = ProductRequestDTO.builder()
                .name("상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.")
                .price(32000)
                .size(ProductSize.M)
                .stock(150)
                .info("상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.")
                .imagesUrl("/product/images/test1.jpg")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.TOP.getLong())
                .build();

        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("info:상품 상세 설명은 100자 이하로 입력 가능합니다.")))
               .andExpect(content().string(containsString("name:상품명은 45자 이하로 입력 가능합니다.")))
               .andDo(print());
    }
    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("test")
                .price(1000)
                .stock(12)
                .size(ProductSize.L)
                .imagesUrl("/test/img")
                .bigCategory(ProductCategory.TOP)
                .smallCategory(ProductCategory.PANTS.getLong())
                .info("테스트 정보")
                .build();

        mockMvc.perform(patch("/product/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(status().isOk())
               .andDo(print());
    }
    @Test
    @DisplayName("상품명과 상품 상세 설명의 글자수가 45자, 100자가 넘어갈 경우 실패해야한다.")
    void updateProductStringLimit() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.")
                .stock(10)
                .size(ProductSize.S)
                .price(100000)
                .imagesUrl("/test/test")
                .bigCategory(ProductCategory.PANTS)
                .smallCategory(ProductCategory.PANTS.getLong())
                .info("상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.")
                .build();

        mockMvc.perform(patch("/product/29")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("info:상품 상세 설명은 100자 이하로 입력 가능합니다.")))
               .andExpect(content().string(containsString("name:상품명은 45자 이하로 입력 가능합니다.")))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }
    @Test
    @DisplayName("상품명에 null, 공백이 들어갈 경우 실패해야한다.")
    void updateProductNameFail() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name(null)
                .stock(10)
                .size(ProductSize.S)
                .price(100000)
                .imagesUrl("/test/test")
                .bigCategory(ProductCategory.PANTS)
                .smallCategory(ProductCategory.PANTS.getLong())
                .info("test")
                .build();

        mockMvc.perform(patch("/product/29")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("name:상품명은 필수 입력 값입니다.")))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }
    @Test
    @DisplayName("재고나 가격에 음수가 들어가면 실패해야한다.")
    void updateProductNegativeFail() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("test")
                .stock(-10)
                .size(ProductSize.S)
                .price(-100000)
                .imagesUrl("/test/test")
                .bigCategory(ProductCategory.PANTS)
                .smallCategory(ProductCategory.PANTS.getLong())
                .info("test")
                .build();

        mockMvc.perform(patch("/product/29")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("stock:상품 재고는 0이상의 값만 입력 가능합니다.")))
               .andExpect(content().string(containsString("price:상품 가격은 0이상의 값만 입력 가능합니다.")))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }
    @Test
    @DisplayName("사이즈, 카테고리(big, small), 정보에 Null값이 들어가면 실패해야 한다.")
    void updateProductNull() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("test")
                .stock(10)
                .size(null)
                .price(100000)
                .imagesUrl("/test/test")
                .bigCategory(null)
                .smallCategory(null)
                .info(null)
                .build();

       mockMvc.perform(patch("/product/29")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
              .andExpect(content().string(containsString("Status:700")))
              .andExpect(content().string(containsString("bigCategory:카테고리는 필수 입력 값입니다.")))
              .andExpect(content().string(containsString("smallCategory:소 카테고리는 필수 입력 값입니다.")))
              .andExpect(content().string(containsString("info:상품 상세 설명은 필수 입력 값입니다.")))
              .andExpect(content().string(containsString("size:상품 사이즈는 필수 입력 값입니다.")))
              .andExpect(status().isBadRequest())
              .andDo(print());
    }
    @Test
    @DisplayName("상품 조회 성공")
    void getProduct() throws Exception {
        ProductResponseDTO.ResponseDTO response = ProductResponseDTO.ResponseDTO.builder()
                .productId(31L)
                .name("test")
                .price(1000)
                .stock(12)
                .size(ProductSize.L)
                .info("테스트 정보")
                .categoryResponseDTO(new ProductResponseDTO.CategoryResponseDTO(ProductCategory.PANTS, ProductCategory.PANTS.getShort()))
                .productImagesResponseDTO(new ProductResponseDTO.ProductImagesResponseDTO("test/etes.img"))
                .build();

        mockMvc.perform(get("/product/31")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(response)))
               .andExpect(status().isOk())
               .andDo(print());
    }
    @Test
    @DisplayName("상품 없을 시 Exception 발생 ( Status : 901, Message : 없는 상품입니다.)")
    void getProductFail() throws Exception {
        doThrow(new APIException(ErrorCode.NOT_FOUND_PRODUCT)).when(productService).getProduct(31L);

        mockMvc.perform(get("/product/31")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(result -> Assertions.assertThrows(APIException.class, () -> productService.getProduct(31L)))
               .andExpect(jsonPath("$.status").value(901))
               .andExpect(jsonPath("$.message").value("없는 상품입니다."))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }
    @Test
    @DisplayName("관리자가 상품 삭제를 성공한다.")
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/product/delete/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andExpect(status().isOk())
               .andDo(print());
    }
}