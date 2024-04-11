package examples.managers;

import examples.command.commands.Command;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;

import java.util.*;

/**
 * class for manipulation by collection Command with our different command
 */
public class CommandManager {
    private final List<String> commandHistory = new ArrayList<>();

    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
    }

    public void addCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public Collection<Command> getCommands() {
        return this.commands.values();
    }

    public void execute(String name, String args) throws NoSuchCommand, ExitObliged, CommandRuntimeError {
        Command command = (Command)this.commands.get(name);
        if (command == null) {
            throw new NoSuchCommand();
        } else {
            command.execute(args);
        }
    }

    public void addToHistory(String line){
        if(line.isBlank()) return;
        this.commandHistory.add(line);
    }

    public List<String> getCommandHistory(){return commandHistory;}

}
