package org.example.command.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.exceptions.NoSuchId;
import org.example.managers.CollectionManager;

/**
 * update value collection element id Обновляет
 */
public class UpdateID extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public UpdateID(Printable console, CollectionManager collectionManager) {
        super("update_id", " {element}: обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public String execute(Request request) {
        try {
            long id = Long.parseLong(request.getArg().trim());
            if (!collectionManager.checkID(id)) throw new NoSuchId();

            console.println(ConsoleColor.toColor("Введите новое значение id для объекта Person...", ConsoleColor.CYAN));

            long newId = 10;

            collectionManager.updateId(id, newId);
            String txt = "Изменение id объекта Person успешно";
            console.println(ConsoleColor.toColor(txt, ConsoleColor.CYAN));
            return "Изменение id объекта Person успешно";
        } catch (NoSuchId noSuchId) {
            long id = Long.parseLong(request.getArg().trim());
            String txt = "Объекта с id " + id + " нет в коллекции. Введите верный id, чтобы изменить объект...";
            console.printError(txt);
            return ConsoleColor.RED + "Объекта с id " + id + " нет в коллекции. Введите верный id, чтобы изменить объект...";
        } catch (NumberFormatException formatException) {
            String txt = "id должно быть числом типа long";
            console.printError(txt);
            return ConsoleColor.RED + "id должно быть числом типа long";
        }
    }


}
