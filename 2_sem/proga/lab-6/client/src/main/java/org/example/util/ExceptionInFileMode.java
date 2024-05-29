package org.example.exceptions;

public class ExceptionInFileMode extends RuntimeException {
    private String message;

    public ExceptionInFileMode() {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
