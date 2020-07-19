package com.alikorkmaz.shoppingcart.exception;

public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String param) {
        super(String.format("invalid parameter for %s", param));
    }
}
