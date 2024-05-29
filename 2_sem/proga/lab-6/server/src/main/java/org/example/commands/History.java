package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CommandManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * prints the last 5 commands (without their arguments)
 */
public class History extends Command {
    private CommandManager commandManager;

    public History(CommandManager commandManager) {
        super("history", ": выводит последние 5 команд (без их аргументов)");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        List<String> history = commandManager.getCommandHistory();

        String txt = history.stream()
                .skip(Math.max(0, history.size() - 5))
                .map(command -> ConsoleColor.toColor(command, ConsoleColor.CYAN))
                .collect(Collectors.joining("\n"));

        return new Response(ResponseStatus.OK, txt);
    }
}