package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.managers.CommandManager;

import java.util.List;

/**
 * prints the last 5 commands (without their arguments)
 */
public class History extends Command {
    private Printable console;

    private CommandManager commandManager;

    public History(Printable console, CommandManager commandManager) {
        super("history", ": выводит последние 5 команд (без их аргументов)");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String a) {
        List<String> history = commandManager.getCommandHistory();

        history.stream()
                .skip(Math.max(0, history.size() - 5))
                .map(command -> ConsoleColor.toColor(command, ConsoleColor.CYAN))
                .forEach(console::println);

        /*
        for (String command : history.subList(Math.max(history.size() - 5, 0), history.size())) {
            console.println(ConsoleColor.toColor(command, ConsoleColor.CYAN));
        }
        */
    }
}