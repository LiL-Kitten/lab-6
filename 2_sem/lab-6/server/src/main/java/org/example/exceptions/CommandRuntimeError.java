package org.example.exceptions;

import java.io.IOException;

public class CommandRuntimeError extends IOException {
    private String message;

    public CommandRuntimeError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
