package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CommandManager;

import java.util.List;
import java.util.stream.Collectors;

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
    public String execute(Request request) {
        List<String> history = commandManager.getCommandHistory();

        String txt = history.stream()
                .skip(Math.max(0, history.size() - 5))
                .map(command -> ConsoleColor.toColor(command, ConsoleColor.CYAN))
                .collect(Collectors.joining("\n"));

        console.println(txt);

        return txt;

        /*
        for (String command : history.subList(Math.max(history.size() - 5, 0), history.size())) {
            console.println(ConsoleColor.toColor(command, ConsoleColor.CYAN));
        }
        */
    }
}