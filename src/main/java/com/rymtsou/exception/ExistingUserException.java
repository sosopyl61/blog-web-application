package com.rymtsou.exception;

public class ExistingUserException extends Exception {
    String login;

    public ExistingUserException(String login) {
        super("Login is already used: " + login);
    }

    @Override
    public String toString() {
        return "Login is already used: " + login;
    }
}
