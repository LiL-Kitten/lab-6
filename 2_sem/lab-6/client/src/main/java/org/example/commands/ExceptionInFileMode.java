package org.example.commands;

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
