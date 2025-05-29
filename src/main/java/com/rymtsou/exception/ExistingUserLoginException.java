package com.rymtsou.exception;

public class ExistingUserLoginException extends Exception {
    String login;

    public ExistingUserLoginException(String login) {
        super("Login is already used: " + login);
    }

    @Override
    public String toString() {
        return "Login is already used: " + login;
    }
}
