package org.example.exceptions;

public class EmptyFileException extends Exception {
    private String message;

    public EmptyFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
