package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.data.Person;
import org.example.managers.CollectionManager;

import java.util.NoSuchElementException;

/**
 * removes the first element from the collection, but prints it out first
 */
public class RemoveHead extends Command {
    private CollectionManager collectionManager;

    public RemoveHead( CollectionManager collectionManager) {
        super("remove_head", ": вывести первый элемент коллекции и удалить его");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (collectionManager.getCollection() == null) {
                return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Коллекция пуста, удалять нечего((");
            } else {
                Person first = collectionManager.getFirst();
                collectionManager.removeFirst();
                return new Response(ResponseStatus.OK, "Первый элемент коллекции удален: " + first);
            }
        } catch (NoSuchElementException e) {
            return new Response(ResponseStatus.ERROR, "Такого ID нет!");
        }

    }
}
