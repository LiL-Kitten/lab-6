package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.Console;

/**
 * Ending the program (without saving to a file)
 */
public class Exit extends Command {
    Console console = new Console();

    public Exit() {
        super("exit", ": Завершение программы!");
    }

    @Override
    public String execute(Request request) {
        //написать код для отключения от сервера
        return "Дотвидания =(";
    }
}
