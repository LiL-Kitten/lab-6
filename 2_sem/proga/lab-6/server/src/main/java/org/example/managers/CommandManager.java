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

    public CommandManager( CollectionManager collectionManager, FileManager fileManager) {
        isScriptRecursionExecuted = false;

        addCommand(new Add(collectionManager));
        addCommand(new Help(this));
        addCommand(new Exit(fileManager, collectionManager));
        addCommand(new Show(collectionManager));
        addCommand(new RemoveHead(collectionManager));
        addCommand(new RemoveByID(collectionManager));
        addCommand(new RemoveElements( collectionManager));
        addCommand(new PrintPassportID( collectionManager));
        addCommand(new UpdateID( collectionManager));
        addCommand(new History(this));
        addCommand(new GroupByDate( collectionManager));
        addCommand(new FilterByName( collectionManager));
        addCommand(new Info( collectionManager));
        addCommand(new Clear( collectionManager));
        addCommand(new Save( fileManager, collectionManager));
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
            Response response = command.execute(request);
            String txt = String.valueOf(response);
            return new Response(response.getStatus(), txt);
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


