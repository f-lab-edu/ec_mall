package com.example.ec_mall.integration;

import com.example.ec_mall.dto.enums.ProductCategory;
import com.example.ec_mall.dto.enums.ProductSize;
import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private ProductRequestDTO productRequestDTO;
    private UpdateProductRequestDTO updateProductRequestDTO;

    @BeforeEach
    void init() {
        productRequestDTO = ProductRequestDTO.builder()
                .name("테스트")
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
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
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
               .andDo(print());
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

        mockMvc.perform(post("/product")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productRequestDTO)))
               .andDo(print());
    }
    @Test
    @DisplayName("가격과 재고에 음수 값이 입력되면 실패해야한다.")
    void addProductPOZ() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
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
               .andDo(print());
    }
    @Test
    @DisplayName("상품명과 상품 상세 설명의 글자수가 45자, 100자가 넘어갈 경우 실패해야한다.")
    void addProductSize() throws Exception{
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
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

        mockMvc.perform(patch("/product/29").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("info:상품 상세 설명은 100자 이하로 입력 가능합니다.")))
               .andExpect(content().string(containsString("name:상품명은 45자 이하로 입력 가능합니다.")))
               .andExpect(status().isBadRequest()).andDo(print());
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

        mockMvc.perform(patch("/product/29").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("name:상품명은 필수 입력 값입니다.")))
               .andExpect(status().isBadRequest()).andDo(print());
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

        mockMvc.perform(patch("/product/29").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("stock:상품 재고는 0이상의 값만 입력 가능합니다.")))
               .andExpect(content().string(containsString("price:상품 가격은 0이상의 값만 입력 가능합니다.")))
               .andExpect(status().isBadRequest()).andDo(print());
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

        mockMvc.perform(patch("/product/29").contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updateProductRequestDTO)))
               .andExpect(content().string(containsString("Status:700")))
               .andExpect(content().string(containsString("bigCategory:카테고리는 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("smallCategory:소 카테고리는 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("info:상품 상세 설명은 필수 입력 값입니다.")))
               .andExpect(content().string(containsString("size:상품 사이즈는 필수 입력 값입니다.")))
               .andExpect(status().isBadRequest()).andDo(print());
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
    @Test
    @DisplayName("productId가 잘 못된 경우 상품 삭제를 실패해야한다.")
    void deleteProductError() throws Exception{
        mockMvc.perform(delete("/product/delete/TEST"))
               .andExpect(status().is4xxClientError())
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

        mockMvc.perform(get("/product/31").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response))).andExpect(status().isOk()).andDo(print());
    }
}