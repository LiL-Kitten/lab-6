package org.example.commands;

import org.example.dth.Request;
import org.example.data.Person;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;

import org.example.managers.CollectionManager;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * displays the passportID field values ​​of all elements in descending order
 */
public class PrintPassportID extends Command {

    private CollectionManager collectionManager;

    public PrintPassportID(CollectionManager collectionManager) {
        super("print_passport_id", ": выводит все значения passportID всех элементов в порядке убывания");

        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty()) {
            String txt = "Коллекция пустая, нечего выводить";
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + txt);
        }
        String txt = collectionManager.getCollection().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Person::getPassportID).reversed())
                .map(Person::getPassportID)
                .collect(Collectors.joining("\n"));

        return new Response(ResponseStatus.OK, txt);
            /*
            List<Person> sortedList = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(Person::getPassportID).reversed())
                    .collect(Collectors.toList());

            for (Person person : sortedList) {
                console.println(person.getPassportID());
            }
            */

    }
}
