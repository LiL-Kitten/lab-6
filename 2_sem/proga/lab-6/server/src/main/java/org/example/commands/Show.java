package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * print in standard output all collection elements
 */
public class Show extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public Show(Printable console, CollectionManager collectionManager) {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {
        var collection = collectionManager.getCollection();
        if (collection == null) {
            return ConsoleColor.RED +"Коллекция не инициализирована";
        } else if (collection.isEmpty()) {
            return ConsoleColor.YELLOW + "Коллекция пустая";
        } else {
            System.out.println(collection.stream()
                    .map(Object::toString)
                    .peek(System.out::println)
                    .collect(Collectors.joining("\n")));
            return collection.stream()
                    .map(Object::toString)
                    .peek(System.out::println)
                    .collect(Collectors.joining("\n"));
        }

    }
}



