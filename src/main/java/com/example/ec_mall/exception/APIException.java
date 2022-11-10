package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class APIException extends RuntimeException{
    private final ErrorCode errorCode;
}
