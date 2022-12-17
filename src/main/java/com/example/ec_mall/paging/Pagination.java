package com.example.ec_mall.paging;

import lombok.Data;

@Data
public class Pagination {
    private int pageSize = 20;  // 페이지 당 보여지는 게시글의 최대 개수
    private int blockSize = 10; // 페이징된 버튼의 블럭당 최대 개수
    private int page = 1;       // 현재 페이지
    private int block = 1;      // 현재 블럭
    private int totalListCnt;   // 총 게시글 수
    private int totalPageCnt;   // 총 페이지 수
    private int totalBlockCnt;  // 총 블럭 수
    private int startPage = 1;  // 블럭 시작 페이지
    private int endPage = 1;    // 블럭 마지막 페이지
    private int startIndex = 1; // DB 접근 시작 index
    private int prevBlock;      // 이전 블럭의 마지막 페이지
    private int nextBlock;      // 다음 블럭의 시작 페이지

    public Pagination(int totalListCnt, int page) {
        /**
         * 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.
         * 총 게시물 수	- totalListCnt
         * 현재 페이지	- page
         */
        this.page = page;                                                       // 현재 페이지
        this.totalListCnt = totalListCnt;                                       // 총 게시글 수
        this.totalPageCnt = ((int) Math.ceil(totalListCnt * 1.0 / pageSize));   // 총 페이지 수
        this.totalBlockCnt = ((int) Math.ceil(totalPageCnt * 1.0 / blockSize)); // 총 블럭 수
        this.block = ((int) Math.ceil((page * 1.0)/blockSize));                 // 현재 블럭
        this.startPage = ((block - 1) * blockSize + 1);                         // 블럭 시작 페이지
        this.endPage = (startPage + blockSize - 1);                             // 블럭 마지막 페이지

        /* === 블럭 마지막 페이지에 대한 validation ===*/
        if(endPage > totalPageCnt){
            this.endPage = totalPageCnt;
        }

        this.prevBlock = ((block * blockSize) - blockSize);                     // 이전 블럭(클릭 시, 이전 블럭 마지막 페이지)

        /* === 이전 블럭에 대한 validation === */
        if(prevBlock < 1) {
            this.prevBlock = 1;
        }

        this.nextBlock = ((block * blockSize) + 1);                             // 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지)

        /* === 다음 블럭에 대한 validation ===*/
        if(nextBlock > totalPageCnt) {
            nextBlock = totalPageCnt;
        }

        this.startIndex = ((page-1) * pageSize);                                // DB 접근 시작 index
    }
}
