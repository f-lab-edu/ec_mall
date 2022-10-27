package com.example.ec_mall.service;

import com.example.ec_mall.dto.ProductDTO;
import com.example.ec_mall.dto.ProductFormDTO;
import com.example.ec_mall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public void addProduct(ProductFormDTO productFormDTO){
        ProductDTO productDTO = ProductDTO.builder()
                .name(productFormDTO.getName())
                .price(productFormDTO.getPrice())
                .size(productFormDTO.getSize())
                .stock(productFormDTO.getStock())
                .info(productFormDTO.getInfo())
                .createdBy("admin")
                .createdDate(LocalDateTime.now())
                .updatedBy("admin")
                .updatedDate(LocalDateTime.now())
                .build();

        productMapper.addProduct(productDTO);
        productMapper.addProductCategory(productDTO);
        productMapper.addProductImages(productDTO);
    }
}
