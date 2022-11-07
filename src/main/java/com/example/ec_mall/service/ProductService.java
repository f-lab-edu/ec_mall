package com.example.ec_mall.service;

import com.example.ec_mall.dao.Product;
import com.example.ec_mall.dao.ProductCategory;
import com.example.ec_mall.dao.ProductImages;
import com.example.ec_mall.dto.ProductRequestDTO;
import com.example.ec_mall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    public void addProduct(ProductRequestDTO productRequestDTO){
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .size(productRequestDTO.getSize().toString())
                .stock(productRequestDTO.getStock())
                .info(productRequestDTO.getInfo())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProduct(product);
    }
    public void addProductCategory(ProductRequestDTO productRequestDTO){
        ProductCategory productCategory = ProductCategory.builder()
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProductCategory(productCategory);
    }
    public void addProductImages(ProductRequestDTO productRequestDTO){
        ProductImages productImages = ProductImages.builder()
                .imagesUrl(productRequestDTO.getImagesUrl())
                .createdBy("admin")
                .createdBy("admin")
                .build();

        productMapper.addProductImages(productImages);
    }
}
