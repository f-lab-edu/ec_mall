package com.example.ec_mall.service;

import com.example.ec_mall.dao.*;
import com.example.ec_mall.dto.request.ProductRequestDTO;
import com.example.ec_mall.dto.request.UpdateProductRequestDTO;
import com.example.ec_mall.dto.response.ProductResponseDTO.ResponseDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j2
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
                .updatedBy("admin")
                .build();

        productMapper.addProductImages(productImages);
    }

    /**
     * 상품 조회
    /**
     * 상품 조회
     *
     * @param id 조회할 상품의 product_id
     */
    public List<ResponseDTO> getProduct(long id){
        return productMapper.findByProductId(id);
    }

    /**
     * 상품 수정 API
     * @param updateProductRequestDTO 업데이트 정보
     * @param id 변경할 상품의 product_id
     */
    public void updateProduct(UpdateProductRequestDTO updateProductRequestDTO, Long id){
        boolean checkProduct = getProduct(id).size() == 0;
        if(checkProduct) {
            log.error("is not Existed Product, Product Id : {}", id);
            throw new APIException(ErrorCode.NOT_FOUND_PRODUCT);
        }
        UpdateProductDao update = UpdateProductDao.builder()
                    .productId(id)
                    .categoryId(productMapper.findCategoryId(id))
                    .name(updateProductRequestDTO.getName())
                    .price(updateProductRequestDTO.getPrice())
                    .stock(updateProductRequestDTO.getStock())
                    .size(updateProductRequestDTO.getSize())
                    .info(updateProductRequestDTO.getInfo())
                    .imagesUrl(updateProductRequestDTO.getImagesUrl())
                    .bigCategory(updateProductRequestDTO.getBigCategory())
                    .smallCategory(updateProductRequestDTO.getSmallCategory())
                    .updatedBy("admin")
                    .build();
        productMapper.updateProduct(update);
    }
    public void deleteProduct(Long productId){
        productMapper.deleteProduct(productId);
    }
}
