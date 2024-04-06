package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.managers.CommandManager;

import java.util.List;

/**
 * prints the last 5 commands (without their arguments)
 */
public class History extends Command
{
    private Console console;

    private CommandManager commandManager;

    public History(Console console, CommandManager commandManager)
    {
        super("history", ": выводит последние 5 команд (без их аргументов)");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException
    {
        if (!a.isBlank()) throw new IllegalArgumentException();
        List<String> history = commandManager.getCommandHistory();
        for (String command : history.subList(Math.max(history.size() - 5, 0), history.size()))
        {
            console.println(ConsoleColor.toColor(command, ConsoleColor.CYAN));
        }
    }
}