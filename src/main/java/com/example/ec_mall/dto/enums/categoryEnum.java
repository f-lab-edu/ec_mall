package com.example.ec_mall.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum categoryEnum {
    TOP("Short-Top", "Long-Top"),
    PANTS("Short-Pants", "Long-Pants");

    private String Short;
    private String Long;
}
