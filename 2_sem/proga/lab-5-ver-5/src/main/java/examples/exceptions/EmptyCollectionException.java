package examples.exceptions;

public class EmptyCollectionException extends RuntimeException {
    private String message;

    public EmptyCollectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
