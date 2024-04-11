package examples.command.commands;

import examples.command.Console;
import examples.data.Person;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * displays the passportID field values ​​of all elements in descending order
 */
public class PrintPassportID extends Command
{
    private Console console;
    private CollectionManager collectionManager;
    public PrintPassportID(Console console, CollectionManager collectionManager)
    {
        super("print_passport_id", ": выводит все значения passportID всех элементов в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException, ExitObliged, CommandRuntimeError
    {
        if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty())
        {
            console.printError("Коллекция пустая, нечего выводить");
            return;
        }

        List<Person> sortedList = collectionManager.getCollection().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Person::getPassportID).reversed())
                .collect(Collectors.toList());

        for (Person person : sortedList)
        {
            console.println(person.getPassportID());
        }
    }
}
