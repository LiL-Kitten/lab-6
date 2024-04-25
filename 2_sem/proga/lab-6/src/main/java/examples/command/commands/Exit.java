package examples.command.commands;

import examples.command.Console;
import examples.exceptions.ExitObliged;

/**
 * Ending the program (without saving to a file)
 */
public class Exit extends Command {
    Console console = new Console();

    public Exit() {
        super("exit", ": Завершение программы!");
    }

    @Override
    public void execute(String a) throws ExitObliged {
        throw new ExitObliged("Дотвидания =(");
    }
}
