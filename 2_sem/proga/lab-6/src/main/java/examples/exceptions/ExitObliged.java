package examples.exceptions;

public class ExitObliged extends Exception {
    private String message;

    public ExitObliged(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
