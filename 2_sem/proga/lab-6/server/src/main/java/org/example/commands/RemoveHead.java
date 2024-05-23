package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.data.Person;
import org.example.managers.CollectionManager;

import java.util.NoSuchElementException;

/**
 * removes the first element from the collection, but prints it out first
 */
public class RemoveHead extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public RemoveHead(Printable console, CollectionManager collectionManager) {
        super("remove_head", ": вывести первый элемент коллекции и удалить его");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {
        try {
            if (collectionManager.getCollection() == null) {
                console.printError("Коллекция пуста, удалять нечего((");
                return ConsoleColor.RED + "Коллекция пуста, удалять нечего((";
            } else {
                Person first = collectionManager.getFirst();
                console.println("Первый элемент коллекции удален: " + first);
                collectionManager.removeFirst();
                return "Первый элемент коллекции удален: " + first;
            }
        }catch (NoSuchElementException e) {
                return "Такого ID нет!";
        }

    }
}