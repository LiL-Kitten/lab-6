package org.example.exceptions;

public class InvalidForm extends Exception {
    private String message;

    public InvalidForm() {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
