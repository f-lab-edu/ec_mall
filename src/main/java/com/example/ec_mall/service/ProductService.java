package com.example.ec_mall.service;

import com.example.ec_mall.dao.ProductDao;
import com.example.ec_mall.dao.ProductImagesDao;
import com.example.ec_mall.dao.UpdateProductDao;
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

//        ProductCategoryDao productCategory = ProductCategoryDao.builder()
//                .productId(product.getProductId())
//                .createdBy("admin")
//                .updatedBy("admin")
//                .build();
//
//        productMapper.addProductCategory(productCategory);

        ProductImagesDao productImages = ProductImagesDao.builder()
                .productId(product.getProductId())
                .imagesUrl(productRequestDTO.getImagesUrl())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        productMapper.addProductImages(productImages);
    }

    /**
     * 상품 수정 API
     * @param updateProductDao 업데이트 정보
     * @param id 변경할 상품의 product_id
     */
    public void updateProduct(UpdateProductDao updateProductDao, Long id){
        UpdateProductDao update = UpdateProductDao.builder()
                .productId(id)
                .name(updateProductDao.getName())
                .price(updateProductDao.getPrice())
                .stock(updateProductDao.getStock())
                .info(updateProductDao.getInfo())
                .imagesUrl(updateProductDao.getImagesUrl())
                .bigCategory(updateProductDao.getBigCategory())
                .smallCategory(updateProductDao.getSmallCategory())
                .updatedBy("admin")
                .build();
        productMapper.updateProduct(update);
    }
}
