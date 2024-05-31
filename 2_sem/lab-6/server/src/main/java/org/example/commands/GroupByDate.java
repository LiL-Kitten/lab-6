package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.Printable;
import org.example.util.ConsoleColor;
import org.example.data.Person;
import org.example.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * groups collection elements by the value of the creationDate field, display the number of elements in each group
 */
public class GroupByDate extends Command {

    private CollectionManager collectionManager;

    public GroupByDate(CollectionManager collectionManager) {
        super("group_by_date", ": сгруппировать элементы коллекции по значению поля creationDate, выводит количество элементов в каждой группе");

        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        String txt = collectionManager.getCollection().stream()
                .collect(Collectors.groupingBy(Person::getCreationDate, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return new Response(ResponseStatus.OK,ConsoleColor.BLUE+"Группировка по дате создания: "+ txt);

    }

}
