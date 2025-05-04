package com.rymtsou.exception;

public class SecurityNotFoundException extends RuntimeException {
    public SecurityNotFoundException(String message) {
        super(message);
    }
}
