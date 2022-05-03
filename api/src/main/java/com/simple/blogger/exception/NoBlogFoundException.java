package com.simple.blogger.exception;

public class NoBlogFoundException extends RuntimeException {
    private final String MSG;

    public NoBlogFoundException(Long id) {
        super();
        this.MSG = "No Blog found with id: " + id;
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
