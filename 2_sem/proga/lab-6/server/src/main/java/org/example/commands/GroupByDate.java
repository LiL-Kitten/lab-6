package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.Printable;
import org.example.util.ConsoleColor;
import org.example.data.Person;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * groups collection elements by the value of the creationDate field, display the number of elements in each group
 */
public class GroupByDate extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public GroupByDate(Printable console, CollectionManager collectionManager) {
        super("group_by_date", ": сгруппировать элементы коллекции по значению поля creationDate, выводит количество элементов в каждой группе");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {
        String txt = collectionManager.getCollection().stream()
                .collect(Collectors.groupingBy(Person::getCreationDate, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));

        console.println(ConsoleColor.BLUE + "Группировка по дате создания:" + txt);
        return ConsoleColor.BLUE+"Группировка по дате создания: "+ txt;

    }

}
