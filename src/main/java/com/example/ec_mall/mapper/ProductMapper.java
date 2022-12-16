package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.*;
import com.example.ec_mall.dto.response.ProductResponseDTO;
import com.example.ec_mall.paging.PageUtil.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(ProductDao product);
    void addCategory(CategoryDao category);
    void addProductCategory(ProductCategoryDao productCategory);
    long findCategoryId(long productId);
    void addProductImages(ProductImagesDao productImages);
    void deleteProduct(long productId);
    List<ProductResponseDTO.ResponseDTO> findByProductId(long productId);
    void updateProduct(UpdateProductDao updateProductDao);
    List<ProductPage> productPage(int startIndex, int pageSize);
    int productPageCount();
}