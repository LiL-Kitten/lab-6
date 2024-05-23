package examples.managers;

import com.thoughtworks.xstream.io.StreamException;
import examples.command.Printable;
import examples.command.ConsoleColor;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * a class for working with all available commands, passing arguments to them
 */
public class RuntimeManager {
    private static Printable console;
    private static InputManager input = new InputManager();
    private static CommandManager commandManager;
    private static FileManager fileManager;

    public RuntimeManager(Printable console) {
        this.console = console;
    }

    public void interactiveMode() throws RuntimeException {
        CollectionManager collectionManager = new CollectionManager();
        fileManager = new FileManager(console, collectionManager);
        commandManager = new CommandManager(console, collectionManager, fileManager);

        Scanner userScanner = new ScannerManager().getInput();
        while (true) {
            try {
                String userCommand = userScanner.nextLine().trim() + " ";
                String[] cmd = userCommand.split(" ", 2);
                if (cmd.length > 1) {
                    cmd[1] = cmd[1].trim(); // убираю пробелы вокруг аргумента
                }
                commandManager.addToHistory(userCommand);
                this.launch(cmd);
            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
                break;
            } catch (NoSuchCommand noSuchCommand) {
                console.printError("Такой команды нет в списке");
                console.println(ConsoleColor.BLUE + "Введите " + ConsoleColor.GREEN + "help" + ConsoleColor.BLUE + " для того чтобы вывести список команд");
            } catch (CommandRuntimeError e) {
                console.printError("Ошибка при исполнении команды");
            } catch (ExitObliged exitObliged) {
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void launch(String[] userCommand) throws IOException, ExitObliged, IllegalArgumentException {
        if (userCommand[0].equals("")) return;
        commandManager.execute(userCommand[0], userCommand[1]);
    }
}
