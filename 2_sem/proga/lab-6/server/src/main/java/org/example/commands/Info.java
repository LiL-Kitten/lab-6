package org.example.command.commands;


import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

/**
 * Prints information about the collection (type, initialization date, number of elements, etc.) to the standard output stream.
 */
public class Info extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public Info(Printable console, CollectionManager collectionManager) {
        super("info", ": вывести в стандартный поток вывода информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(Request request) {
        String lastInitDate = (collectionManager.getLastInitDate() == null)
                ? "Коллекция не инициализирована "
                : collectionManager.getLastInitDate().toString();
        String lastSaveDate = (collectionManager.getLastSaveDate() == null)
                ? "Коллекция не сохранялась ранее"
                : collectionManager.getLastSaveDate().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Сведения о коллекции")
                .append(ConsoleColor.toColor("\nТип: ", ConsoleColor.BLUE) + collectionManager.collectionType() + "\n")
                .append(ConsoleColor.toColor("Количество элементов: ", ConsoleColor.BLUE) + collectionManager.collectionSize() + "\n")
                .append(ConsoleColor.toColor("Дата последней инициализации: ", ConsoleColor.BLUE) + lastInitDate + "\n")
                .append(ConsoleColor.toColor("Дата последней изменения: ", ConsoleColor.BLUE) + lastSaveDate + "\n");
        console.print(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
