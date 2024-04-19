package examples;


import examples.command.Console;
import examples.command.commands.*;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;
import examples.managers.CollectionManager;
import examples.managers.CommandManager;
import examples.managers.FileManager;
import examples.managers.RuntimeManager;



import java.util.NoSuchElementException;

public class Main
{
    static CollectionManager collectionManager = new CollectionManager();
    static Console console = new Console();

    public static void main(String[] args) throws ExitObliged, NoSuchCommand, NoSuchElementException {
        FileManager fileManager = new FileManager(console, collectionManager);
       try
        {
            fileManager.findFile();
            fileManager.create();
        }
        catch (ExitObliged e) {}

        CommandManager commandManager = new CommandManager();
        RuntimeManager runtimeManager = new RuntimeManager(console,commandManager);
        commandManager.useCommands(console, collectionManager, fileManager, runtimeManager);
        runtimeManager.interactiveMode();

    }

}