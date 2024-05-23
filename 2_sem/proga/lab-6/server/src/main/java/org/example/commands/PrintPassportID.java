package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.data.Person;
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
    private Printable console;
    private CollectionManager collectionManager;

    public PrintPassportID(Printable console, CollectionManager collectionManager) {
        super("print_passport_id", ": выводит все значения passportID всех элементов в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {

        if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty()) {
            String txt = "Коллекция пустая, нечего выводить";
            console.printError(txt);
            return ConsoleColor.RED + txt;
        }
        String txt = collectionManager.getCollection().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Person::getPassportID).reversed())
                .map(Person::getPassportID)
                .collect(Collectors.joining("\n"));
        console.println(txt);

        return txt;
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
