package examples.exceptions;

public class ExitObliged extends  InterruptedException  {
    private String message;


    public ExitObliged() {
    }


    public ExitObliged(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
