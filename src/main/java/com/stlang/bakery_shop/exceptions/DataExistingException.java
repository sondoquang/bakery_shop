package com.stlang.bakery_shop.exceptions;

public class DataExistingException extends RuntimeException {
    public DataExistingException(String phoneNumberAlreadyExists) {
        super(phoneNumberAlreadyExists);
    }
}
