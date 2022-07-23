package com.simple.blogger.exception;

public class InvalidAuthorException extends RuntimeException {
    private final String MSG;

    public InvalidAuthorException(String user) {
        super();
        this.MSG = "Invalid author exception: " + user;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + MSG;
    }

    @Override
    public String getMessage() {
        return MSG;
    }
}
