package com.rymtsou.exception;

public class ExistingUsernameException extends Exception {
    String username;

    public ExistingUsernameException(String username) {
        super("Username is already used: " + username);
    }

    @Override
    public String toString() {
        return "Username is already used: " + username;
    }
}
