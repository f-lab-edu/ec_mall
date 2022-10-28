package com.example.ec_mall.error;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException(String msg){
        super(msg);
    }
}
