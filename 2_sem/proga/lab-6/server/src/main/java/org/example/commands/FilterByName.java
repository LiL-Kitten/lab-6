package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * prints all elements and values of the name field that begin with the given substring
 */
public class FilterByName extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public FilterByName(Printable console, CollectionManager collectionManager) {
        super("filter_by_name", ": вывести элементы, значение  поля name которых начинается с заданной подстроки");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {
        if (collectionManager.getCollection().isEmpty()) {
            return  ConsoleColor.RED + "Коллекци пустая =(";
        }

        if (request == null) {
            console.printError("Не указана подстрока для фильтрации.");
            return  ConsoleColor.RED + "Не указана подстрока для фильтрации.";
        }

        String namePrefix = request.getArg().trim();
        String txt = collectionManager.getCollection().stream()
                .filter(person -> person.getName().startsWith(namePrefix))
                .map(person -> "Результат фильтрации:\n" + person)
                .collect(Collectors.joining("\n"));
        console.println(txt);
        return  txt;
    }

}