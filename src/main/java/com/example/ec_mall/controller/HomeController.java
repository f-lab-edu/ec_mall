package com.example.ec_mall.controller;

import com.example.ec_mall.paging.PageUtil.*;
import com.example.ec_mall.paging.Pagination;
import com.example.ec_mall.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/home", consumes = "application/json")
public class HomeController {
    private final HomeService homeService;

    /**
     *
     * @RequestParam vs @PathVariable
     *  => @RequestParam 은 쿼리 문자열에서 값을 추출하고 @PathVariable는 URI 경로에서 값을 추출합니다.
     *     @RequestParam의 경우 key=value 형태로 값을 보내는 방식이다.
     */
    @GetMapping("/app")
    public ResponseEntity<List<ProductPage>> home(@RequestParam(defaultValue = "1") int page){
        // 총 게시물 수
        int totalListCnt = homeService.productPageCount();

        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        List<ProductPage> pagingList = homeService.home(startIndex, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(pagingList);
    }
}
