package examples.command.commands;

import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;

import java.io.IOException;

/**
 * Interface for executing any commands
 */
public interface Executable {
    public void execute(String a) throws ExitObliged, IOException, IllegalArgumentException;
}
