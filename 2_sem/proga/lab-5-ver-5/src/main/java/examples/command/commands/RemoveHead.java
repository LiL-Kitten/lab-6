package examples.command.commands;

import examples.command.Printable;
import examples.data.Person;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

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
    public void execute(String a) {
        try {
            if (collectionManager.getCollection() == null) {
                console.printError("Коллекция пуста, удалять нечего((");
            } else {
                Person first = collectionManager.getFirst();
                console.println("Первый элемент коллекции удален: " + first);
                collectionManager.removeFirst();
            }
        }catch (NoSuchElementException e) {

        }

    }
}
