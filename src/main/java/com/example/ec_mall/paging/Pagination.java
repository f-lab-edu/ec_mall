package com.example.ec_mall.paging;

import com.example.ec_mall.dto.ProductPageDTO;
import lombok.Getter;

@Getter
public class Pagination {
    private int totalRecordCount;   // 전체 데이터 수
    private int totalPageCount;     // 전체 페이지 수
    private int startPage;          // 첫 페이지 번호
    private int endPage;            // 끝 페이지 번호
    private int limitStart;         // LIMIT 시작 위치
    private boolean existPrevPage;  // 이전 페이지 존재 여부
    private boolean existNextPage;  // 다음 페이지 존재 여부

    public Pagination(int totalRecordCount, ProductPageDTO productPageDTO) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.calculation(productPageDTO);
        }
    }

    private void calculation(ProductPageDTO productPageDTO) {
        if (totalPageCount > 0) {

            // 전체 페이지 수 계산
            totalPageCount = ((totalRecordCount - 1) / productPageDTO.getRecordSize()) + 1;

            // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
            if (productPageDTO.getPage() > totalPageCount) {
                productPageDTO.setPage(totalPageCount);
            }

            // 첫 페이지 번호 계산
            startPage = ((productPageDTO.getPage() - 1) / productPageDTO.getPageSize()) * productPageDTO.getPageSize() + 1;

            // 끝 페이지 번호 계산
            endPage = startPage + productPageDTO.getPageSize() - 1;

            // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
            if (endPage > totalPageCount) {
                endPage = totalPageCount;
            }

            // LIMIT 시작 위치 계산
            limitStart = (productPageDTO.getPage() - 1) * productPageDTO.getRecordSize();

            // 이전 페이지 존재 여부 확인
            existPrevPage = startPage != 1;

            // 다음 페이지 존재 여부 확인
            existNextPage = (endPage * productPageDTO.getRecordSize()) < totalRecordCount;
        }
    }
}
