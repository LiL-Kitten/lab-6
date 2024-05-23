package org.example.commands;


import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.exceptions.EmptyCollectionException;

import org.example.managers.CollectionManager;

/**
 * Clear collection
 */
public class Clear extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public Clear(Printable console, CollectionManager collectionManager) {
        super("clear", ": очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * clears the entire collection
     *
     * @param request
     * @return
     * @throws EmptyCollectionException
     */
    @Override
    public Response execute(Request request) {
           try {
               collectionManager.clear();
               console.println("Элементы удалены");
               return new Response(ResponseStatus.OK,ConsoleColor.BLUE + "Элементы удалены");
           } catch (EmptyCollectionException e) {
               console.printError("Коллекция пуста");
               return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Коллекция пуста");
           }

    }
}
