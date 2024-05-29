package org.example.commands;


import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;

/**
 * Prints information about the collection (type, initialization date, number of elements, etc.) to the standard output stream.
 */
public class Info extends Command {
    private CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", ": вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
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
        return new Response(ResponseStatus.OK, stringBuilder.toString());
    }
}
