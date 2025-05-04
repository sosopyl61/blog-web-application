package com.rymtsou.exception;

public class LoginUsedException extends RuntimeException {
    public LoginUsedException(String login) {
        super("Login already exists: " + login);
    }
}
