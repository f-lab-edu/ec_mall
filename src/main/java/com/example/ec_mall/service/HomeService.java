package com.example.ec_mall.service;

import com.example.ec_mall.dao.PagingDao;
import com.example.ec_mall.dto.request.PagingRequestDTO;
import com.example.ec_mall.dto.response.PagingResponseDTO;
import com.example.ec_mall.mapper.ProductMapper;
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
    public List<PagingResponseDTO> homePaging(PagingRequestDTO pagingRequestDTO, int startIndex, int pageSize){
        PagingDao pageDao = PagingDao.builder()
                .name(pagingRequestDTO.getName())
                .imagesUrl(pagingRequestDTO.getImagesUrl())
                .bigCategory(pagingRequestDTO.getBigCategory())
                .smallCategory(pagingRequestDTO.getSmallCategory())
                .startIndex(startIndex)
                .pageSize(pageSize)
                .build();
        return productMapper.productPage(pageDao);
    }
}
