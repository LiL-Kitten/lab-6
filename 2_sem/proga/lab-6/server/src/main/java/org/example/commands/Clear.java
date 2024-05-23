package org.example.command.commands;


import org.example.dth.Request;
import org.example.dth.Response;
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
    public String execute(Request request) {
           try {
               collectionManager.clear();
               console.println("Элементы удалены");
               return ConsoleColor.BLUE + "Элементы удалены";
           } catch (EmptyCollectionException e) {
               console.printError("Коллекция пуста");
               return ConsoleColor.RED + "Коллекция пуста";
           }

    }
}
