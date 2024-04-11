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
        commandManager.addCommand(new Add(console, collectionManager));
        commandManager.addCommand(new Help(console, commandManager));
        commandManager.addCommand(new Exit());
        commandManager.addCommand(new Show(console,collectionManager));
        commandManager.addCommand(new RemoveHead(console, collectionManager));
        commandManager.addCommand(new RemoveByID(console, collectionManager));
        commandManager.addCommand(new RemoveElements(console, collectionManager));
        commandManager.addCommand(new PrintPassportID(console, collectionManager));
        commandManager.addCommand(new UpdateID(console, collectionManager));
        commandManager.addCommand(new History(console, commandManager));
        commandManager.addCommand(new GroupByDate(console, collectionManager));
        commandManager.addCommand(new FilterByName(console, collectionManager));
        commandManager.addCommand(new Info(console, collectionManager));
        commandManager.addCommand(new Clear(console, collectionManager));
        commandManager.addCommand(new Save(console, fileManager));
        commandManager.addCommand(new ExecuteScript(console, fileManager, commandManager));
        runtimeManager.interactiveMode();

    }

}