package com.example.ec_mall.service;

import com.example.ec_mall.mapper.ProductMapper;
import com.example.ec_mall.paging.Pagination;
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

    public List<Paging> home(int page){
        // 총 게시물 수
        int totalListCnt = productMapper.productPageCount();

        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);

        // DB select start index
        int startIndex = pagination.getStartIndex();

        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        return productMapper.productPage(startIndex, pageSize);
    }
}
