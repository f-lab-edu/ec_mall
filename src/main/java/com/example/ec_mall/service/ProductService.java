package com.example.ec_mall.service;

import com.example.ec_mall.dao.*;
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
        ProductDao product = ProductDao.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .size(productRequestDTO.getSize())
                .stock(productRequestDTO.getStock())
                .info(productRequestDTO.getInfo())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProduct(product);

        CategoryDao category = CategoryDao.builder()
                .bigCategory(productRequestDTO.getBigCategory())
                .smallCategory(productRequestDTO.getSmallCategory())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addCategory(category);

        ProductCategoryDao productCategory = ProductCategoryDao.builder()
                .productId(product.getProductId())
                .categoryId(category.getCategoryId())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProductCategory(productCategory);

        ProductImagesDao productImages = ProductImagesDao.builder()
                .productId(product.getProductId())
                .imagesUrl(productRequestDTO.getImagesUrl())
                .createdBy("admin")
                .createdBy("admin")
                .build();

        productMapper.addProductImages(productImages);
    }
    public void deleteProduct(Long productId){
        productMapper.deleteProduct(productId);
    }
}
