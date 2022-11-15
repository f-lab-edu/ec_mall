package com.example.ec_mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = e.getBindingResult(); // BindingResult 은 검증오류를 보관하는 객체이다.

        /*
         *   StringBuffer, StringBuilder 클래스
         *     => StringBuffer Thread safe 하며, StringBuilder Thread safe 하지 않다.
		          append() 메소드를 이용해서 값을 더할 수 있으며, 문자열을 더하더라도 새로운 객체를 생성하지 않는다.
         */
        StringBuffer stringBuffer = new StringBuffer();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuffer.append("[Status:" + errorCode.getStatus()).append(", ");
            stringBuffer.append(fieldError.getField()).append(":");
            stringBuffer.append(fieldError.getDefaultMessage()).append("]");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stringBuffer.toString());
    }
}
