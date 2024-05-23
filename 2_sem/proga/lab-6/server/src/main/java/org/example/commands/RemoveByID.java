package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;

import org.example.managers.CollectionManager;

/**
 * updates the value of a collection element whose id is equal to the given one
 */
public class RemoveByID extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public RemoveByID(Printable console, CollectionManager collectionManager) {
        super("remove_by_id", ": удалить определенный элемент коллекции по id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public String execute(Request request) {
        class NoSuchId extends RuntimeException {
        }

        if (collectionManager.getCollection() == null) {
            console.printError("Простите но коллекция пуста");
            return  ConsoleColor.RED +"Простите но коллекция пуста";
        } else {
            try {
                long id = Long.parseLong(request.getArg().trim());
                if (!collectionManager.checkID(id)) throw new NoSuchId();
                collectionManager.removeByID(id);
                String txt = "Удаление объекта с id = " + id + " завершено успешно...";
                console.println(ConsoleColor.toColor(txt, ConsoleColor.CYAN));
                return  ConsoleColor.toColor(txt, ConsoleColor.CYAN);
            } catch (NoSuchId idException) {
                int id1 = Integer.parseInt(request.getArg().trim());
                String txt = "Объекта с id " + id1 + " нет в коллекции. Введите элемент, ID котрого есть в коллекции, чтобы удалить его";
                console.printError(txt);
                return  ConsoleColor.toColor(txt, ConsoleColor.RED);
            } catch (NumberFormatException formatException) {
                String txt = "Значение id должно быть типа int";
                console.printError(txt);
                return  txt;
            }

        }
    }
}
