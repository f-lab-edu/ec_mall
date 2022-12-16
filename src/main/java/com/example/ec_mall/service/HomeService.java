package com.example.ec_mall.service;

import com.example.ec_mall.mapper.ProductMapper;
import com.example.ec_mall.paging.PagingUtil.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {
    private final ProductMapper productMapper;

    public int productPageCount(){
        return productMapper.productPageCount();
    }
    public List<Paging> home(int startIndex, int pageSize){
        return productMapper.productPage(startIndex, pageSize);
    }
}
