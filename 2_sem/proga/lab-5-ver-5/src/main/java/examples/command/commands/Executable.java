package examples.command.commands;

import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;

/**
 * Interface for executing any commands
 */
public interface Executable
{
    public void execute(String a) throws IllegalArgumentException, ExitObliged, CommandRuntimeError;
}
