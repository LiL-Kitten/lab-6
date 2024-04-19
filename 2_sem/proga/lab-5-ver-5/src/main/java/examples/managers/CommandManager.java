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

    public CommandManager() {
        isScriptRecursionExecuted = false;
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

    public void useCommands(Printable console, CollectionManager collectionManager, FileManager fileManager, RuntimeManager runtimeManager) {
        this.addCommand(new Add(console, collectionManager));
        this.addCommand(new Help(console, this));
        this.addCommand(new Exit());
        this.addCommand(new Show(console, collectionManager));
        this.addCommand(new RemoveHead(console, collectionManager));
        this.addCommand(new RemoveByID(console, collectionManager));
        this.addCommand(new RemoveElements(console, collectionManager));
        this.addCommand(new PrintPassportID(console, collectionManager));
        this.addCommand(new UpdateID(console, collectionManager));
        this.addCommand(new History(console, this));
        this.addCommand(new GroupByDate(console, collectionManager));
        this.addCommand(new FilterByName(console, collectionManager));
        this.addCommand(new Info(console, collectionManager));
        this.addCommand(new Clear(console, collectionManager));
        this.addCommand(new Save(console, fileManager));
        this.addCommand(new ExecuteScript(console, fileManager, this));
        runtimeManager.interactiveMode();

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
