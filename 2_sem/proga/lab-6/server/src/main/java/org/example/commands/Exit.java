package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
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
    public Response execute(Request request) {
        //написать код для отключения от сервера
        return new Response(ResponseStatus.EXIT,"Дотвидания =(");
    }
}
