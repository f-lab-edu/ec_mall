package com.example.ec_mall.controller;

import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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

    /*
        @PathVariable => URI를 통해 전달된 값을 파라미터로 받아오는 역할을 하며, 값을 하나만 받아올 수 있다.
     */
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
