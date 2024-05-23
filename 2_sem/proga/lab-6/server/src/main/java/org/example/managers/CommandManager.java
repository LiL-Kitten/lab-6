package org.example.managers;

import org.example.commands.*;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.exceptions.ExitObliged;
import org.example.exceptions.NoSuchCommand;
import org.example.util.Console;
import org.example.util.Printable;

import java.io.IOException;
import java.util.*;

/**
 * class for manipulation by collection Command with our different command
 */
public class CommandManager {

    Console console = new Console();
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
        addCommand(new Save(console, fileManager, collectionManager));
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

    public Response execute(Request request) throws IOException, ExitObliged, IllegalArgumentException {
        Command command = this.commands.get(request.getCommand());

        if (command == null) {
            return new Response(ResponseStatus.ERROR, "такой команды нет!");
        } else {
            String txt = String.valueOf(command.execute(request));
            return new Response(command.execute(request).getStatus(), txt);
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
