package examples.managers;

import examples.command.Printable;
import examples.command.Console;
import examples.command.ConsoleColor;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * a class for working with all available commands, passing arguments to them
 */
public class RuntimeManager {
    private static Printable console;

    private static InputManager input = new InputManager();
    private static CommandManager commandManager;

    public RuntimeManager(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }


    public void interactiveMode() {
        Scanner userScanner = new ScannerManager().getInput();
        while (true) {
            try {

                if (!userScanner.hasNext()) throw new ExitObliged();
                String userCommand = userScanner.nextLine().trim() + " ";
                String[] cmd = userCommand.split(" ", 2);
                if (cmd.length > 1) {
                    cmd[1] = cmd[1].trim(); // убираю пробелы вокруг аргумента
                }
                commandManager.addToHistory(userCommand);
                this.launch(cmd);
            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            } catch (NoSuchCommand noSuchCommand) {
                console.printError("Такой команды нет в списке");
                console.println(ConsoleColor.BLUE + "Введите "+ConsoleColor.GREEN+"help"+ ConsoleColor.BLUE+" для того чтобы вывести список команд");
            } catch (IllegalArgumentException e) {
                console.printError("Введены неправильные аргументы команды");
            } catch (CommandRuntimeError e) {
                console.printError("Ошибка при исполнении команды");
            } catch (ExitObliged exitObliged) {
                return;
            }
        }
    }

    public void launch(String[] userCommand) throws NoSuchCommand, ExitObliged, IllegalArgumentException, CommandRuntimeError {
        if (userCommand[0].equals("")) return;
        commandManager.execute(userCommand[0], userCommand[1]);
    }
}

