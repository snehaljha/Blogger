package com.simple.blogger.exception;

public class UserAlreadyExistException extends Exception {
    private final String MSG;

    public UserAlreadyExistException(String username) {
        super();
        MSG = "User " + username + " already exists.";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + MSG;
    }

    @Override
    public String getMessage() {
        return MSG;
    }
}
