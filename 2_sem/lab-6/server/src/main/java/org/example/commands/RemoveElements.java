package org.example.commands;

import org.example.data.Location;
import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;

import org.example.managers.CollectionManager;

import java.util.Collection;
import java.util.Objects;

/**
 * Removes all elements from the collection that are smaller than the specified one
 */
public class RemoveElements extends Command {

    private CollectionManager collectionManager;

    public RemoveElements(CollectionManager collectionManager) {
        super("remove_elements", ": удалить все элементы которые меньше заданного");

        this.collectionManager = collectionManager;

    }

    @Override
    public Response execute(Request request) {
        class NoElement extends RuntimeException {
        }

        try {

            if (collectionManager.getCollection() == null || collectionManager.getCollection().isEmpty()) {
                return new Response(ResponseStatus.EXIT, "Невозномно выполнить удаление данного элемента т.к\nКоллекция пустая");
            }
            Location location = request.getObj().getLocation();
            Collection<Person> whatNeedRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(person -> person.compareTo(location) <= -1)
                    .toList();

            collectionManager.removeElements(whatNeedRemove);
            return new Response(ResponseStatus.OK,"Из коллекции удалены элемены, меньше заданного");
        } catch (NoElement e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,ConsoleColor.RED + "В коллекции нет элементов");
        }
    }

}
