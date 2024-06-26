package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.Printable;

import org.example.managers.ExecuteFileManager;
import org.example.util.ConsoleColor;
import org.example.exceptions.CommandRuntimeError;
import org.example.exceptions.ExitObliged;
import org.example.exceptions.NoSuchCommand;
import org.example.managers.CommandManager;
import org.example.managers.FileManager;

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
    public Response execute(Request request) {
        if (request == null ) {
            return  new Response(ResponseStatus.ERROR,ConsoleColor.RED+ "Путь не распознан");
        }
        console.println(ConsoleColor.toColor("Путь получен успешно", ConsoleColor.PURPLE));

        File scriptFile = new File(request.getArg());
        File currentFile = executeFileManager.getCurrentFile();
        if (commandManager.isScriptRecursionExecuted() || (currentFile != null && scriptFile.getAbsolutePath()
                .equals(currentFile.getAbsolutePath()))) {
            return  new Response(ResponseStatus.OK,"Рекурсия скрипта уже выполнялась");
        }
        commandManager.setScriptRecursionExecuted(true);

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    commandManager.addToHistory(line);
                    String[] cmd = (line + " ").split(" ", 2);
                    if (request.getCommand().isBlank());
                    console.println(ConsoleColor.toColor("Выполнение команды " + cmd[0], ConsoleColor.YELLOW));
                    commandManager.execute(request);
                    return  new Response(ResponseStatus.ERROR, request.getCommand() + " " + request.getArg());
                } catch (NoSuchElementException exception) {
                    console.printError("Пользовательский ввод не обнаружен!");
                    return new Response(ResponseStatus.EXIT,ConsoleColor.RED+"Пользовательский вовд не обнаружен!");
                } catch (NoSuchCommand noSuchCommand) {
                    console.printError("Такой команды нет в списке");
                    return new Response(ResponseStatus.ERROR,ConsoleColor.RED+"Такой команды нет в списке");
                } catch (IllegalArgumentException e) {
                    console.printError("Введены неправильные аргументы команды");
                    return new Response(ResponseStatus.ERROR,ConsoleColor.RED+"Введены неправильные аргументы команды");
                } catch (CommandRuntimeError e) {
                    console.printError("Ошибка при исполнении команды");
                    return  new Response(ResponseStatus.ERROR,ConsoleColor.RED+"Ошибка при исполнении команды");
                } catch (ExitObliged e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            console.printError("Такого файла не существует");
            return  new Response(ResponseStatus.ERROR,ConsoleColor.RED+"Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
            return  new Response(ResponseStatus.ERROR,ConsoleColor.RED+"Ошибка ввода вывода");
        } finally {
            commandManager.setScriptRecursionExecuted(false);
            return  new Response(ResponseStatus.ERROR,"все");
        }
    }

}
