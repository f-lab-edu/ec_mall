package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.response.ProductPageResponseDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO;
import com.example.ec_mall.service.ProductService;
import com.example.ec_mall.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        productService.addProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<ProductResponseDTO.ResponseDTO>> getProduct(@PathVariable Long id){
        List<ProductResponseDTO.ResponseDTO> productResponseDTO = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    /**
     * UPDATE ( PUT vs PATCH )
     * PUT : 리소스 전체를 변경, cf) id, nickname, email 있을 때 nickname만 변경하면 id, email 값은 null 값이 된다.
     * PATCH : 리소스의 일부분을 변경.
     * @PathVariable : 해당 어노테이션을 사용하여 요청 URI 매핑에서 템플릿 변수를 처리하고 메소드 매개 변수로 설정 할 수 있다.
     *   ex) 기본 키로 Entity를 식별하는 엔드포인트로 사용
     * @param id  변경할 상품의 product_id
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateProductRequestDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequestDTO updateProductRequestDTO){
        productService.updateProduct(updateProductRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

   @GetMapping("/main")
   public ResponseEntity<PagingResponse<ProductResponseDTO>> productPage(@ModelAttribute ProductPageResponseDTO productPageResponseDTO){
        PagingResponse<ProductResponseDTO> product = productService.productPage(productPageResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
