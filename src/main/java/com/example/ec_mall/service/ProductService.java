package com.example.ec_mall.service;

import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.ProductCategoryDao;
import com.example.ec_mall.dao.ProductImagesDao;
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
                .size(productRequestDTO.getSize().toString())
                .stock(productRequestDTO.getStock())
                .info(productRequestDTO.getInfo())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProduct(product);

        ProductCategoryDao productCategory = ProductCategoryDao.builder()
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProductCategory(productCategory);

        ProductImagesDao productImages = ProductImagesDao.builder()
                .imagesUrl(productRequestDTO.getImagesUrl())
                .createdBy("admin")
                .createdBy("admin")
                .build();

        productMapper.addProductImages(productImages);
    }
}
