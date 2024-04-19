package examples.exceptions;

public class InvalidForm extends Exception {
    private String message;

    public InvalidForm(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
