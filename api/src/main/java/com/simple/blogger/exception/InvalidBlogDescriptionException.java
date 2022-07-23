package com.simple.blogger.exception;

public class InvalidBlogDescriptionException extends RuntimeException {
    
    private final String MSG;

    public InvalidBlogDescriptionException(String description) {
        super();
        this.MSG = "Invalid blog description: " + description;
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
