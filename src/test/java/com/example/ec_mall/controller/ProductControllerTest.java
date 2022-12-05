package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.enums.categoryEnum;
import com.example.ec_mall.dto.enums.sizeEnum;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("상품명과 상품 상세 설명의 글자수가 45자, 100자가 넘어갈 경우 실패해야한다.")
    void updateProductStringLimit() throws Exception {
        updateProductRequestDTO = UpdateProductRequestDTO.builder()
                .name("상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.상품명 테스트입니다.")
                .stock(10)
                .size(sizeEnum.S)
                .price(100000)
                .imagesUrl("/test/test")
                .bigCategory(categoryEnum.PANTS)
                .smallCategory(categoryEnum.PANTS.getLong())
                .info("상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.상품 상세 설명 테스트입니다.")
                .build();

        doNothing().when(productService).updateProduct(updateProductRequestDTO, 29L);

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
                .size(sizeEnum.S)
                .price(100000)
                .imagesUrl("/test/test")
                .bigCategory(categoryEnum.PANTS)
                .smallCategory(categoryEnum.PANTS.getLong())
                .info("test")
                .build();

        doNothing().when(productService).updateProduct(updateProductRequestDTO, 29L);

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
                .size(sizeEnum.S)
                .price(-100000)
                .imagesUrl("/test/test")
                .bigCategory(categoryEnum.PANTS)
                .smallCategory(categoryEnum.PANTS.getLong())
                .info("test")
                .build();

        doNothing().when(productService).updateProduct(updateProductRequestDTO, 29L);

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

        doNothing().when(productService).updateProduct(updateProductRequestDTO, 29L);

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
    @Test
    @DisplayName("한 페이지에 상품이 20개씩 표시되며 페이징 처리를 성공한다.")
    void productPage() throws Exception{
        mockMvc.perform(get("/product/main")
               .param("limitStart", "1")
               .param("recordSize", "20"))
               .andExpect(status().isOk())
               .andDo(print());
    }
}