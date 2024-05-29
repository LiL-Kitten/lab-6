package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * prints all elements and values of the name field that begin with the given substring
 */
public class FilterByName extends Command {

    private CollectionManager collectionManager;

    public FilterByName(CollectionManager collectionManager) {
        super("filter_by_name", ": вывести элементы, значение  поля name которых начинается с заданной подстроки");

        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty()) {
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Коллекци пустая =(");
        }

        if (request == null) {
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Не указана подстрока для фильтрации.");
        }

        String namePrefix = request.getArg().trim();
        String txt = collectionManager.getCollection().stream()
                .filter(person -> person.getName().startsWith(namePrefix))
                .map(person -> "Результат фильтрации:\n" + person)
                .collect(Collectors.joining("\n"));
        return new Response(ResponseStatus.OK, txt);
    }

}
