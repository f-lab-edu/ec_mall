package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.PagingRequestDTO;
import com.example.ec_mall.dto.response.PagingResponseDTO;
import com.example.ec_mall.paging.Pagination;
import com.example.ec_mall.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/app")
    public ResponseEntity<List<PagingResponseDTO>> homePaging(@RequestParam(defaultValue = "1") int page, PagingRequestDTO pagingRequestDTO){
        // 총 게시물 수
        int totalListCnt = homeService.productPageCount();

        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        List<PagingResponseDTO> pagingList = homeService.homePaging(pagingRequestDTO, startIndex, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(pagingList);
    }
}
