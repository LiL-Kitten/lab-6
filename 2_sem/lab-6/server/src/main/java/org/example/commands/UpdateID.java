package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.exceptions.NoSuchId;
import org.example.managers.CollectionManager;

/**
 * update value collection element id Обновляет
 */
public class UpdateID extends Command {
    private CollectionManager collectionManager;

    public UpdateID(CollectionManager collectionManager) {
        super("update_id", " {element}: обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            long id = Long.parseLong(request.getArg().trim());
            if (!collectionManager.checkID(id)) throw new NoSuchId();
            long newId = 10;
            collectionManager.updateId(id, newId);
            String txt = "Изменение id объекта Person успешно";
            return new Response(ResponseStatus.OK, "Изменение id объекта Person успешно");
        } catch (NoSuchId noSuchId) {
            long id = Long.parseLong(request.getArg().trim());
            String txt = "Объекта с id " + id + " нет в коллекции. Введите верный id, чтобы изменить объект...";
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "Объекта с id " + id + " нет в коллекции. Введите верный id, чтобы изменить объект...");
        } catch (NumberFormatException formatException) {
            String txt = "id должно быть числом типа long";
            return new Response(ResponseStatus.ERROR, ConsoleColor.RED + "id должно быть числом типа long");
        }
    }


}
