package examples.exceptions;

public class ExceptionInFileMode extends RuntimeException {
    private String message;

    public ExceptionInFileMode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
