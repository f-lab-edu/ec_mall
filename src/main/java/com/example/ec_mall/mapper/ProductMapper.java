package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.CategoryDao;
import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.ProductCategoryDao;
import com.example.ec_mall.dao.ProductImagesDao;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDao product);
    void addCategory(CategoryDao category);
    void addProductCategory(ProductCategoryDao productCategory);
    void addProductImages(ProductImagesDao productImages);
    void deleteProduct(long productId);
    List<ProductDao> product();
}
