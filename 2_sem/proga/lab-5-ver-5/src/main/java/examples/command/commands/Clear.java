package examples.command.commands;

import examples.command.Console;

import examples.exceptions.EmptyCollectionException;

import examples.managers.CollectionManager;

/**
 * Clear collection
 */
public class Clear extends Command
{
    private Console console;
    private CollectionManager collectionManager;
    public Clear(Console console, CollectionManager collectionManager)
    {
        super("clear", ": очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * clears the entire collection
     * @param a
     * @throws EmptyCollectionException
     */
    @Override
    public void execute(String a) throws EmptyCollectionException
    {
        collectionManager.clear();
        console.println("Элементы удалены");
    }
}
