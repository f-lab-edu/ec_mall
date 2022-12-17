package com.example.ec_mall.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PagingUtil {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImages{
        private String imagesUrl;
    }
    @Data
    @Builder
    public static class Paging {
        private String name;
        private PagingUtil.ProductImages productImages;
    }
}
