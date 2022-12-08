package com.example.ec_mall.dto.response;

import com.example.ec_mall.paging.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponseDTO {
    private int page = 1;             // 현재 페이지 번호
    private int recordSize = 20;      // 페이지당 출력할 데이터 개수
    private int pageSize = 10;        // 화면 하단에 출력할 페이지 사이즈
    private Pagination pagination;    // 페이지네이션 정보
}
