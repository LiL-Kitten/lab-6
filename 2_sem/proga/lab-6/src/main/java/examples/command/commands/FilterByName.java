package examples.command.commands;

import examples.command.Printable;
import examples.data.Person;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

import java.util.List;
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
    public void execute(String a) throws IllegalArgumentException, ExitObliged, CommandRuntimeError {
        if (collectionManager.getCollection().isEmpty()) {
            throw new CommandRuntimeError("Коллекци пустая =(");
        }

        if (a.isEmpty()) {
            console.printError("Не указана подстрока для фильтрации.");
            return;
        }

        String namePrefix = a.trim();
        List<Person> filteredPersons = collectionManager.getCollection().stream()
                .filter(person -> person.getName().startsWith(namePrefix))
                .collect(Collectors.toList());

        console.println("Результат фильтрации:");
        for (Person person : filteredPersons) {
            console.println(String.valueOf(person));
        }
    }

}
