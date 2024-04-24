package examples;


import examples.command.Console;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;
import examples.managers.RuntimeManager;



import java.util.NoSuchElementException;

public class Main
{
    static Console console = new Console();

    public static void main(String[] args) throws ExitObliged, NoSuchCommand, NoSuchElementException {

        RuntimeManager runtimeManager = new RuntimeManager(console);
        runtimeManager.interactiveMode();

    }

}