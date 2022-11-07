package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.Product;
import com.example.ec_mall.dao.ProductCategory;
import com.example.ec_mall.dao.ProductImages;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(Product product);
    void addProductCategory(ProductCategory productCategory);
    void addProductImages(ProductImages productImages);
    List<Product> product();
}
