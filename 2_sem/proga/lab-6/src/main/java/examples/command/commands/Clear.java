package examples.command.commands;


import examples.command.Printable;
import examples.exceptions.EmptyCollectionException;

import examples.managers.CollectionManager;

/**
 * Clear collection
 */
public class Clear extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public Clear(Printable console, CollectionManager collectionManager) {
        super("clear", ": очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * clears the entire collection
     *
     * @param a
     * @throws EmptyCollectionException
     */
    @Override
    public void execute(String a) throws EmptyCollectionException {
           try {
               collectionManager.clear();
               console.println("Элементы удалены");
           } catch (EmptyCollectionException e) {
               console.printError("Коллекция пуста");
           }

    }
}
