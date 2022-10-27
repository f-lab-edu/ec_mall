package com.example.ec_mall.mapper;

import com.example.ec_mall.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDTO product);
    void addProductCategory(ProductDTO productCategory);
    void addProductImages(ProductDTO productImages);
    List<ProductDTO> product();
}
