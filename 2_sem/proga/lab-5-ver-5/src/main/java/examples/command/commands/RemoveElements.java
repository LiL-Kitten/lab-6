package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.data.Person;
import examples.data.forms.PersonForm;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.InvalidForm;
import examples.managers.CollectionManager;

import java.util.Collection;
import java.util.Objects;

/**
 * Removes all elements from the collection that are smaller than the specified one
 */
public class RemoveElements extends Command
{
    private Console console;
    private CollectionManager collectionManager;
    public RemoveElements(Console console, CollectionManager collectionManager) {
        super("remove_elements", ": удалить все элементы которые меньше заданного" );
        this.console = console;
        this.collectionManager = collectionManager;

    }

    @Override
    public void execute(String a) throws  IllegalArgumentException, ExitObliged, CommandRuntimeError {
        if (!a.isBlank()) throw new IllegalArgumentException();
        class NoElement extends RuntimeException {
        }

        try {
            console.println(ConsoleColor.toColor("Введите данные объекта Person", ConsoleColor.CYAN));
            Person newPerson = new PersonForm(console).build();
            Collection<Person> what_need_remove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(person -> person.compareTo(newPerson) <= -1)
                    .toList();

            if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty()) {
                console.printError("Невозномно выполнить удаление данного элемента т.к\nКоллекция пустая");
                return;
            }

            collectionManager.removeElements(what_need_remove);
            console.println(ConsoleColor.toColor("Из коллекции удалены элемены, меньше заданного", ConsoleColor.YELLOW));
        } catch (NoElement e) {
            console.printError("В коллекции нет элементов");
        } catch (InvalidForm e) {
            throw new RuntimeException(e);
        }
    }

}
