package com.simple.blogger.exception;

public class InvalidBlogTitleException extends RuntimeException {
    
    private final String MSG;

    public InvalidBlogTitleException(String title) {
        super();
        this.MSG = "Invalid blog title: " + title;
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
