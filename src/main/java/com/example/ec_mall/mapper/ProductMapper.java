package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDao product);
    void addCategory(CategoryDao categoryDao);
    void addProductCategory(ProductCategoryDao productCategory);
    long findCategoryId(long productId);
    void addProductImages(ProductImagesDao productImages);
    List<ProductDao> product();
    void updateProduct(UpdateProductDao updateProductDao);
}
