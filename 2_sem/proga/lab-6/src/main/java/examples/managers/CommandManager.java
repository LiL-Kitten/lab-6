package examples.managers;

import examples.command.Printable;
import examples.command.commands.*;
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
    private boolean isScriptRecursionExecuted;

    public CommandManager(Printable console, CollectionManager collectionManager, FileManager fileManager) {
        isScriptRecursionExecuted = false;

        addCommand(new Add(console, collectionManager));
        addCommand(new Help(console, this));
        addCommand(new Exit());
        addCommand(new Show(console, collectionManager));
        addCommand(new RemoveHead(console, collectionManager));
        addCommand(new RemoveByID(console, collectionManager));
        addCommand(new RemoveElements(console, collectionManager));
        addCommand(new PrintPassportID(console, collectionManager));
        addCommand(new UpdateID(console, collectionManager));
        addCommand(new History(console, this));
        addCommand(new GroupByDate(console, collectionManager));
        addCommand(new FilterByName(console, collectionManager));
        addCommand(new Info(console, collectionManager));
        addCommand(new Clear(console, collectionManager));
        addCommand(new Save(console, fileManager));
        addCommand(new ExecuteScript(console, fileManager, this));
    }
    public boolean isScriptRecursionExecuted() {
        return isScriptRecursionExecuted;
    }

    public void setScriptRecursionExecuted(boolean scriptRecursionExecuted) {
        isScriptRecursionExecuted = scriptRecursionExecuted;
    }

    public void addCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public Collection<Command> getCommands() {
        return this.commands.values();
    }

    public void execute(String name, String args) throws NoSuchCommand, ExitObliged, CommandRuntimeError, IllegalArgumentException {
        Command command = (Command) this.commands.get(name);
        if (command == null) {
            throw new NoSuchCommand("такой команды нет!");
        } else {
            command.execute(args);
        }
    }

    public void addToHistory(String line) {
        if (line.isBlank()) return;
        this.commandHistory.add(line);
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }


}
