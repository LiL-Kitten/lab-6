package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.managers.CollectionManager;

/**
 * print in standard output all collection elements
 */
public class Show extends Command
{
    private Console console;
    private CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager)
    {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException
    {
        if (!a.isBlank()) throw new IllegalArgumentException();
        var collection = collectionManager.getCollection();
        if (collection == null) {console.printError("Коллекция не инициализирована");}
        else if (collection.isEmpty()) {console.println(ConsoleColor.YELLOW + "Коллекция пустая");}
        else {collection.forEach(System.out::println);}
    }
}



