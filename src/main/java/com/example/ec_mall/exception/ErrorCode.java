package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(700, "Invalid Input Value"),;

    private final int status;
    private final String message;
}
