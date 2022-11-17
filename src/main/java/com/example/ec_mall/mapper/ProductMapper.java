package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.ProductCategoryDao;
import com.example.ec_mall.dao.ProductImagesDao;
import com.example.ec_mall.dao.UpdateProductDao;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDao product);
//    void addProductCategory(ProductCategoryDao productCategory);
    void addProductImages(ProductImagesDao productImages);
    List<ProductDao> product();

    void updateProduct(UpdateProductDao updateProductDao);
}
