package com.project.vinylata.Exception;

public class JWTNotValidateException extends RuntimeException{
    public JWTNotValidateException(String msg){
        super(msg);
    }
}
