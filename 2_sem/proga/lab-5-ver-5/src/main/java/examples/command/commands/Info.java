package examples.command.commands;


import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.managers.CollectionManager;

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
    public void execute(String a) {
        String lastInitDate = (collectionManager.getLastInitDate() == null)
                ? "Коллекция не инициализирована"
                : collectionManager.getLastInitDate().toString();
        String lastSaveDate = (collectionManager.getLastSaveDate() == null)
                ? "Коллекция не инициализирована"
                : collectionManager.getLastSaveDate().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Сведения о коллекции")
                .append(ConsoleColor.toColor("\nТип: ", ConsoleColor.BLUE) + collectionManager.collectionType() + "\n")
                .append(ConsoleColor.toColor("Количество элементов: ", ConsoleColor.BLUE) + collectionManager.collectionSize() + "\n")
                .append(ConsoleColor.toColor("Дата последней инициализации: ", ConsoleColor.BLUE) + lastInitDate + "\n")
                .append(ConsoleColor.toColor("Дата последней изменения: ", ConsoleColor.BLUE) + lastSaveDate + "\n");
        console.print(stringBuilder.toString());

    }
}
