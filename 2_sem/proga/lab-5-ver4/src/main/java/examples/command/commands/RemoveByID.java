package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

/**
 * updates the value of a collection element whose id is equal to the given one
 */
public class RemoveByID extends Command
{
    private Console console;
    private CollectionManager collectionManager;
    public RemoveByID(Console console, CollectionManager collectionManager)
    {
        super("remove_by_id", ": удалить определенный элемент коллекции по id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException, ExitObliged, CommandRuntimeError
    {
        class NoSuchId extends RuntimeException{}

        if(collectionManager.getCollection() == null)
        {
            console.printError("Простите но коллекция пуста");
        } else
        {
            try
            {
            long id = Long.parseLong(a.trim());
            if (!collectionManager.checkID(id)) throw new NoSuchId();
            collectionManager.removeByID(id);
            console.println(ConsoleColor.toColor("Удаление объекта с id = " + id + " завершено успешно...", ConsoleColor.CYAN));
            }
            catch (NoSuchId idException)
            {
                int id1 = Integer.parseInt(a.trim());
                console.printError("Объекта с id " + id1 + " нет в коллекции. Введите элемент, ID котрого есть в коллекции, чтобы удалить его");
            }
            catch (NumberFormatException formatException){console.printError("Значение id должно быть типа int");}

        }
    }
}
