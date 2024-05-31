package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * print in standard output all collection elements
 */
public class Show extends Command {

    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        var collection = collectionManager.getCollection();
        if (collection == null) {
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Коллекция не инициализирована");
        } else if (collection.isEmpty()) {
            return new Response(ResponseStatus.OK, ConsoleColor.YELLOW + "Коллекция пустая");
        } else {
            return new Response(ResponseStatus.OK, collection.stream()
                    .map(Object::toString)
                    .peek(System.out::println)
                    .collect(Collectors.joining("\n")));
        }

    }
}



