package com.project.vinylata.Exception;

public class CartItemNotExistException extends RuntimeException{
    public CartItemNotExistException(String msg){
        super(msg);
    }
}
