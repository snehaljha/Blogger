package com.simple.blogger.exception;

public class EmptyBlogException extends RuntimeException {
    private final String MSG;

    public EmptyBlogException(String content) {
        super();
        this.MSG = "Invalid blog title: " + content;
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
