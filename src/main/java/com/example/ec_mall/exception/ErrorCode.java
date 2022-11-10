package com.example.ec_mall.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //400 Bad request
    INVALID_PASSWORD(400,"유효하지 않은 비밀번호 입니다."),
    //409 Conflict
    ALREADY_SAVED_EMAIL(409, "이미 가입된 이메일입니다.");

    private final int status;
    private final String message;
}
