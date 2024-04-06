package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.managers.CollectionManager;
import examples.managers.CommandManager;

import java.util.Iterator;

/**
 * displays help on available commands
 */
public class Help extends Command
{
    private Console console;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    public Help(Console console, CommandManager commandManager)
    {
        super("help", ": Вывести справку по командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException
    {
        Iterator<Command> iterator = commandManager.getCommands().iterator();
        while (iterator.hasNext())
        {
            Command command = iterator.next();
            String commandInfo = ConsoleColor.toColor(command.getName(), ConsoleColor.GREEN) + command.getDescription();
            console.println(commandInfo);
        }
    }
}
