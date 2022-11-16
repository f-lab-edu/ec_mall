package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //800 회원가입 - 규칙에 위배된 정보 입력
    INVALID_INPUT_VALUE(700,""),
    //801 회원가입 - 중복된 이메일
    ALREADY_SAVED_EMAIL(801, "이미 가입된 이메일입니다.");

    private final int status;
    private final String message;
}
