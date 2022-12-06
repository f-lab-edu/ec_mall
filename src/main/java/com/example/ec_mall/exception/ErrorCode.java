package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //700번대 공통 - 규칙에 위배된 정보 입력
    INVALID_INPUT_VALUE(700, "Invalid Input Value"),

    //800번대 회원
    //801 - 중복된 이메일
    ALREADY_SAVED_EMAIL(801, "이미 가입된 이메일입니다."),
    NOT_FOUND_ACCOUNT(802, "회원정보를 찾을 수 없습니다."),

    //900번대 상품
    //901 - 없는 상품
    NOT_FOUND_PRODUCT(901, "없는 상품입니다.");

    private final int status;
    private final String message;
}
