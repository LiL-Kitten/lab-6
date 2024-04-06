package examples.command.commands;

import examples.managers.ExecuteFileManager;
import examples.command.Console;
import examples.command.ConsoleColor;
import examples.exceptions.CommandRuntimeError;
import examples.exceptions.ExitObliged;
import examples.exceptions.NoSuchCommand;
import examples.managers.CommandManager;
import examples.managers.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * One of the most steamy commands, necessary to read and execute a script from the specified file
 * The script contains commands in the same form in which the user enters them in interactive mode.
 */
public class ExecuteScript extends Command
{
    private Console console;
    private CommandManager commandManager;

    private FileManager fileManager;
    public ExecuteScript(Console console, FileManager fileManager, CommandManager commandManager)
    {
        super("execute_script", ": считать и исполнить скрипт из указанного файла");
        this.console = console;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String args) throws IllegalArgumentException
    {
        if (args == null || args.isEmpty())
        {
            console.printError("Путь не распознан");
            return;
        }
        else console.println(ConsoleColor.toColor("Путь получен успешно", ConsoleColor.PURPLE));

        try
        {
            Console.setFileMode(true);
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                try
                {
                    commandManager.addToHistory(line);
                    String[] cmd = (line + " ").split(" ", 2);
                    if (cmd[0].isBlank()) return;
                    if (cmd[0].equals("execute_script"))
                    {
                        if(ExecuteFileManager.fileRepeat(cmd[1]))
                        {
                            console.printError("Найдена рекурсия по пути " + new File(cmd[1]).getAbsolutePath());
                            continue;
                        }
                    }
                    console.println(ConsoleColor.toColor("Выполнение команды " + cmd[0], ConsoleColor.YELLOW));
                    commandManager.execute(cmd[0], cmd[1]);
                    if (cmd[0].equals("execute_script"))
                    {
                        ExecuteFileManager.popFile();
                    }
                }
                catch (NoSuchElementException exception) {console.printError("Пользовательский ввод не обнаружен!");}
                catch (NoSuchCommand noSuchCommand) {console.printError("Такой команды нет в списке");}
                catch (IllegalArgumentException e) {console.printError("Введены неправильные аргументы команды");}
                catch (CommandRuntimeError e) {console.printError("Ошибка при исполнении команды");}
                catch (ExitObliged e) {throw new RuntimeException(e);}
            }
            ExecuteFileManager.popFile();
        }
        catch (NoSuchCommand noSuchCommand){console.printError("Такой команды не существует");}
        catch (FileNotFoundException fileNotFoundException){console.printError("Такого файла не существует");}
        catch (IOException e) {console.printError("Ошибка ввода вывода");}
        Console.setFileMode(false);
    }
}

