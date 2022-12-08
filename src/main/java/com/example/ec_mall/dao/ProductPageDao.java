package com.example.ec_mall.dao;

import com.example.ec_mall.paging.Pagination;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductPageDao {
    private List list = new ArrayList<>();
    private final Pagination pagination;

    public ProductPageDao(List list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }
}
