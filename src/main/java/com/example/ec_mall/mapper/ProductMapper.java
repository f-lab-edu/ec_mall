package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.CategoryDao;
import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.ProductCategoryDao;
import com.example.ec_mall.dao.ProductImagesDao;
import com.example.ec_mall.dao.UpdateProductDao;
import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.response.ProductPageResponseDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO;
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
    List<ProductRequestDTO> findProductInfoById(long productId);
    void updateProduct(UpdateProductDao updateProductDao);
    List<ProductResponseDTO> productPage(ProductPageResponseDTO productPageResponseDTO);
    int productPageCount(ProductPageResponseDTO productPageResponseDTO);
}
