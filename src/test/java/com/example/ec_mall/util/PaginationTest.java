package com.example.ec_mall.util;

import com.example.ec_mall.dto.response.ProductPageResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PaginationTest {
    private ProductPageResponseDTO productPageResponseDTO;

    @BeforeEach
    void setUp(){
        productPageResponseDTO = ProductPageResponseDTO.builder()
                .page(1)
                .recordSize(20)
                .pageSize(10)
                .build();
    }

    @Test
    @DisplayName("페이지 수, 페이지 번호, LIMIT 시작 위치 값을 정상적으로 계산하는지 테스트")
    void paginationEqual(){
        int totalRecordCount = 24;   // 전체 데이터 수
        int totalPageCount;          // 전체 페이지 수
        int startPage;               // 첫 페이지 번호
        int limitStart;              // LIMIT 시작 위치

        totalPageCount = ((totalRecordCount - 1) / productPageResponseDTO.getRecordSize()) + 1;
        assertThat(totalPageCount).isEqualTo(2);

        startPage = ((productPageResponseDTO.getPage() - 1) / productPageResponseDTO.getPageSize()) * productPageResponseDTO.getPageSize() + 1;
        assertThat(startPage).isEqualTo(1);

        limitStart = (productPageResponseDTO.getPage() - 1) * productPageResponseDTO.getRecordSize();
        assertThat(limitStart).isEqualTo(0);
    }
}