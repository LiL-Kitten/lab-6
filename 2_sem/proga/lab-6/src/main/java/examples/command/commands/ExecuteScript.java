package examples.command.commands;

import examples.command.Printable;

import examples.managers.ExecuteFileManager;
import examples.command.ConsoleColor;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;
import examples.managers.CommandManager;
import examples.managers.FileManager;

import java.io.*;
import java.util.NoSuchElementException;

/**
 * One of the most steamy commands, necessary to read and execute a script from the specified file
 * The script contains commands in the same form in which the user enters them in interactive mode.
 */
public class ExecuteScript extends Command {
    private Printable console;
    private CommandManager commandManager;
    private FileManager fileManager;
    private ExecuteFileManager executeFileManager;

    public ExecuteScript(Printable console, FileManager fileManager, CommandManager commandManager) {
        super("execute_script", ": считать и исполнить скрипт из указанного файла");
        this.console = console;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String args) throws IllegalArgumentException {
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        }
        console.println(ConsoleColor.toColor("Путь получен успешно", ConsoleColor.PURPLE));

        File scriptFile = new File(args);
        File currentFile = executeFileManager.getCurrentFile();
        if (commandManager.isScriptRecursionExecuted() || (currentFile != null && scriptFile.getAbsolutePath()
                .equals(currentFile.getAbsolutePath()))) {
            console.printError("Рекурсия скрипта уже выполнялась");
            return;
        }
        commandManager.setScriptRecursionExecuted(true);

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    commandManager.addToHistory(line);
                    String[] cmd = (line + " ").split(" ", 2);
                    if (cmd[0].isBlank()) return;
                    console.println(ConsoleColor.toColor("Выполнение команды " + cmd[0], ConsoleColor.YELLOW));
                    commandManager.execute(cmd[0], cmd[1]);
                } catch (NoSuchElementException exception) {
                    console.printError("Пользовательский ввод не обнаружен!");
                } catch (NoSuchCommand noSuchCommand) {
                    console.printError("Такой команды нет в списке");
                } catch (IllegalArgumentException e) {
                    console.printError("Введены неправильные аргументы команды");
                } catch (CommandRuntimeError e) {
                    console.printError("Ошибка при исполнении команды");
                } catch (ExitObliged e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        } finally {
            commandManager.setScriptRecursionExecuted(false);
        }
    }

}
