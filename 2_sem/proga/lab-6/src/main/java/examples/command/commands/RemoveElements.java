package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.data.Location;
import examples.data.Person;
import examples.data.forms.LocationForm;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

import java.util.Collection;
import java.util.Objects;

/**
 * Removes all elements from the collection that are smaller than the specified one
 */
public class RemoveElements extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public RemoveElements(Printable console, CollectionManager collectionManager) {
        super("remove_elements", ": удалить все элементы которые меньше заданного");
        this.console = console;
        this.collectionManager = collectionManager;

    }

    @Override
    public void execute(String a) throws ExitObliged, CommandRuntimeError {
        class NoElement extends RuntimeException {
        }

        try {

            if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty()) {
                console.printError("Невозномно выполнить удаление данного элемента т.к\nКоллекция пустая");
                return;
            }

            console.println(ConsoleColor.toColor("Введите данные объекта Location, для того чтобы определить" +
                    " центр и удалить элементы которые удалены от центра", ConsoleColor.CYAN));
            Location location = new LocationForm(console).build();
            Collection<Person> whatNeedRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(person -> person.compareTo(location) <= -1)
                    .toList();

            collectionManager.removeElements(whatNeedRemove);
            console.println(ConsoleColor.toColor("Из коллекции удалены элемены, меньше заданного", ConsoleColor.YELLOW));
        } catch (NoElement e) {
            console.printError("В коллекции нет элементов");
        }
    }

}
