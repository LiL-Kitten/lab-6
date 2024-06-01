package org.example.util;

import org.example.commands.ExecuteScript;
import org.example.commands.Exit;
import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.forms.PersonForm;

import java.util.NoSuchElementException;

public class RuntimeManager {
    private static Printable console;
    private InputManager inputManager;
    private Client client;
    private Exit exit;
    private ExecuteScript executeScript;
    private Response response;


    public RuntimeManager(Printable console, Client client, InputManager inputManager) {
        RuntimeManager.console = console;
        this.client = client;
        this.inputManager = inputManager;
        this.exit = new Exit(console, client, this);
        this.executeScript = new ExecuteScript(console, client, this);

    }

    public void run() {
        client.createSocket();

        while (true) {
            try {
                inputManager.commandInput();

                if (inputManager.getCommand().equalsIgnoreCase("exit")) {
                    exit.execute(inputManager.getArgument());
                } else if (inputManager.getCommand().equalsIgnoreCase("execute_script")) {
                    executeScript.execute(inputManager.getArgument());
                    continue;
                } else {
                    try {
                        response = client.sendAndAskResponse(new Request(inputManager.getCommand(), inputManager.getArgument()));
                    } catch (Exception e) {
                        console.printError("Ошибка при отправке запроса: " + e.getMessage());
                        break;
                    }
                }
                processResponse(response);

            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            } catch (Exception e) {
                console.printError("Произошла ошибка: " + e.getMessage());
            }
        }
    }

    public void processResponse(Response response) {
        if (response != null) {
            this.printResponse(response);

            switch (response.getStatus()) {
                case ASK_OBJECT:
                    Person person = new PersonForm(console).build();
                    if (!person.validate()) {
                        console.printError("Объект некорректен!");
                        return;
                    }

                    client.sendAndAskResponse(new Request("add",person));

                case OK:
                    break;
                case ERROR:
                    console.printError(response.getResponse());
                    break;
                case EXIT:
                    console.println(response.getResponse());
                    break;
                default:
                    console.printError("Неизвестный статус ответа: " + response.getStatus());


            }
        }
    }

    public void printResponse(Response response) {
        if (response == null) {
            console.printError("Не удалось получить ответ от сервера.");
            return;
        }
        console.println(ConsoleColor.BLUE + "Получен ответ с статусом: " + ConsoleColor.RESET + response.getStatus());
        console.println(ConsoleColor.BLUE + "Сообщение: \n" + ConsoleColor.RESET + response.getResponse());
    }
}