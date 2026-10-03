package com.key_stone.exception;

public class CustomerEmailAlreadyExistsException extends RuntimeException {

    public CustomerEmailAlreadyExistsException(String message) {
        super(message);
    }
}