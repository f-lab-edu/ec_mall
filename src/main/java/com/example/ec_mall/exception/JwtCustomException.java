package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class JwtCustomException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;
}
