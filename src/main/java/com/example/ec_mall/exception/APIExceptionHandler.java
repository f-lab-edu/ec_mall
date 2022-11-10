package com.example.ec_mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler({APIException.class})
    public ResponseEntity<Object> handleAPIException(APIException e){
        ErrorCode errorCode = ErrorCode.ALREADY_SAVED_EMAIL;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e){

        ErrorCode errorCode = ErrorCode.INVALID_PASSWORD;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
