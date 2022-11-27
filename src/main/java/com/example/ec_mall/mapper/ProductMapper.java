package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.*;
import com.example.ec_mall.dto.ProductRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDao product);
    void addProductCategory(ProductCategoryDao productCategory);
    long findCategoryId(long productId);
    void addProductImages(ProductImagesDao productImages);
    List<ProductDao> product();
    List<ProductRequestDTO> findProductInfoById(long productId);
    void updateProduct(UpdateProductDao updateProductDao);
}
