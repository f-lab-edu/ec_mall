package com.example.ec_mall.paging;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PagingResponse<T> {
    private List<T> list = new ArrayList<>();
    private final Pagination pagination;

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }
}
