package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.exceptions.NoSuchId;
import examples.managers.CollectionManager;


import java.util.Scanner;

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
    public void execute(String args) throws NoSuchId {
        try {
            long id = Long.parseLong(args.trim());
            if (!collectionManager.checkID(id)) throw new NoSuchId();

            console.println(ConsoleColor.toColor("Введите новое значение id для объекта Person...", ConsoleColor.CYAN));
            Scanner scanner = new Scanner(System.in);
            long newId = scanner.nextLong();

            collectionManager.updateId(id, newId);
            console.println(ConsoleColor.toColor("Изменение id объекта Person успешно", ConsoleColor.CYAN));
        } catch (NoSuchId noSuchId) {
            long id = Long.parseLong(args.trim());
            console.printError("Объекта с id " + id + " нет в коллекции. Введите верный id, чтобы изменить объект...");
        } catch (NumberFormatException formatException) {
            console.printError("id должно быть числом типа long");
        }
    }


}
