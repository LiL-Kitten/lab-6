package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.managers.CollectionManager;
import examples.managers.CommandManager;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * displays help on available commands
 */
public class Help extends Command {
    private Printable console;
    private CollectionManager collectionManager;
    private CommandManager commandManager;

    public Help(Printable console, CommandManager commandManager) {
        super("help", ": Вывести справку по командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String a) {

        commandManager.getCommands().stream()
                .map(command -> ConsoleColor.toColor(command.getName(), ConsoleColor.GREEN) + command.getDescription())
                .forEach(console::println);
        /*
        Iterator<Command> iterator = commandManager.getCommands().iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            String commandInfo = ConsoleColor.toColor(command.getName(), ConsoleColor.GREEN) + command.getDescription();
            console.println(commandInfo);
        }
         */
    }
}
