package org.example.managers;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.exceptions.CommandRuntimeError;
import org.example.exceptions.ExitObliged;
import org.example.exceptions.NoSuchCommand;
import org.example.util.ConsoleColor;
import org.example.util.Printable;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * a class for working with all available commands, passing arguments to them
 */
public class RuntimeManager {
    private Printable console;
    private CommandManager commandManager;
    private FileManager fileManager;

    public RuntimeManager(Printable console) {
        this.console = console;
        CollectionManager collectionManager = new CollectionManager();
        fileManager = new FileManager(console, collectionManager);
        commandManager = new CommandManager(console, collectionManager, fileManager);
    }

    public Response interactiveMode(Request request) throws RuntimeException {
        try {
            commandManager.addToHistory(request.getCommand());
            this.launch(request);
            // Успешное выполнение команды
            return new Response(ResponseStatus.OK, ConsoleColor.GREEN+"Команда выполнена успешно\n"+ ConsoleColor.RESET+ this.launch(request));
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
            return new Response(ResponseStatus.ERROR, "Пользовательский ввод не обнаружен!");
        } catch (NoSuchCommand noSuchCommand) {
            console.printError("Такой команды нет в списке");
            console.println("Введите help для того чтобы вывести список команд");
            return new Response(ResponseStatus.ERROR, "Такой команды нет в списке");
        } catch (CommandRuntimeError e) {
            console.printError("Ошибка при исполнении команды");
            return new Response(ResponseStatus.ERROR, "Ошибка при исполнении команды");
        } catch (ExitObliged exitObliged) {
            return new Response(ResponseStatus.ERROR, "выходим");
        } catch (IOException e) {
            return new Response(ResponseStatus.ERROR, "ошибка");
        }
    }

    public Response launch(Request request) throws IOException, ExitObliged, IllegalArgumentException {
        return commandManager.execute(request);
    }
}