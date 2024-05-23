package examples.exceptions;

import java.io.IOException;

public class NoSuchCommand extends IOException {
    private String message;

    public NoSuchCommand(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

}
