package com.example.ec_mall.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PaginationTest {

    @Test
    @DisplayName("페이지 수, 페이지 번호, LIMIT 시작 위치 값을 정상적으로 계산하는지 테스트")
    void paginationEqual(){
        int totalListCnt = 24;       // 전체 데이터 수
        int page = 1;                // 현재 페이지
        int pageSize = 20;           // 페이지 당 보여지는 게시글의 최대 개수
        int totalPageCnt;            // 총 페이지 수
        int startIndex = 0;          // DB 접근 시작 index

        totalPageCnt = ((int) Math.ceil(totalListCnt * 1.0) / pageSize);
        assertThat(totalPageCnt).isEqualTo(1);

        startIndex = ((page-1) * pageSize);
        assertThat(startIndex).isEqualTo(0);
    }
}