package examples.command.commands;

import examples.command.Printable;
import examples.command.ConsoleColor;
import examples.data.Person;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * groups collection elements by the value of the creationDate field, display the number of elements in each group
 */
public class GroupByDate extends Command {
    private Printable console;
    private CollectionManager collectionManager;

    public GroupByDate(Printable console, CollectionManager collectionManager) {
        super("group_by_date", ": сгруппировать элементы коллекции по значению поля creationDate, выводит количество элементов в каждой группе");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String a) throws ExitObliged, CommandRuntimeError {
        Map<LocalDate, Long> groupedByDate = collectionManager.getCollection().stream()
                .collect(Collectors.groupingBy(Person::getCreationDate, Collectors.counting()));

        console.println(ConsoleColor.BLUE + "Группировка по дате создания:");
        for (Map.Entry<LocalDate, Long> entry : groupedByDate.entrySet()) {
            console.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
