package com.example.ec_mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<Object> handleAPIException(APIException e){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e){


        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
