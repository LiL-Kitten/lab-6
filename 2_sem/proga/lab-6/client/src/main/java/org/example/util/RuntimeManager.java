package org.example.util;

import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.forms.PersonForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class RuntimeManager {
    private static Printable console;
    private ScannerManager userScanner;
    private Client client;

    public RuntimeManager(Printable console, Client client, ScannerManager scannerManager) {
        RuntimeManager.console = console;
        this.client = client;
        this.userScanner = scannerManager;
    }

    public void interactiveMode() throws UnknownHostException {
        client.createSocket();

        while (true) {
            try {
                if (!userScanner.getInput().hasNext()) {
                    console.printError("Ввод пользователя не обнаружен, завершение работы.");
                    System.exit(1);
                }

                String[] userCommand = (userScanner.getInput().nextLine().trim() + " ").split(" ", 2);
                Response response;

                try {
                    response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                } catch (Exception e) {
                    console.printError("Ошибка при отправке запроса: " + e.getMessage());
                    continue;
                }

                processResponse(userCommand, response);

            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            } catch (Exception e) {
                console.printError("Произошла ошибка: " + e.getMessage());
            }
        }
    }

    private void processResponse(String[] userCommand, Response response) {
        if (response != null) {
            this.printResponse(response);

            switch (response.getStatus()) {
                default:
                    console.printError("Неизвестный статус ответа: " + response.getStatus());
                case ASK_OBJECT:
                    Person person = new PersonForm(console).build();
                    if (!person.validate()) {
                        console.printError("Объект некорректен!");
                        return;
                    }

                    Response newResponse;
                    try {
                        newResponse = client.sendAndAskResponse(new Request(userCommand[0].trim(), person));
                    } catch (Exception e) {
                        console.printError("Ошибка при отправке запроса с объектом: " + e.getMessage());
                        return;
                    }

                    if (newResponse != null) {
                        this.printResponse(newResponse);
                    }
                    break;
                case OK:
                    console.println(ConsoleColor.toColor("Все ОК!", ConsoleColor.GREEN));
                    break;
                case ERROR:
                    console.printError(response.getResponse());
                    break;
                case EXIT:
                    client.closeConnection();
                    System.exit(1);
            }

            if ("execute_script".equalsIgnoreCase(userCommand[0].trim())) {
                Console.setFileMode(true);
                this.fileExecution(response.getResponse());
                Console.setFileMode(false);
            }
            if ("exit".equalsIgnoreCase(userCommand[0].trim()))
            {
                client.closeConnection();
                System.exit(1);
            }
        }
    }

    private void printResponse(Response response) {
        if (response == null) {
            console.printError("Не удалось получить ответ от сервера.");
            return;
        }
        console.println("Получен ответ с статусом: " + response.getStatus());
        console.println("Сообщение: " + response.getResponse());
    }


    private void fileExecution(String args) {
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        } else console.println(ConsoleColor.toColor("Путь получен успешно", ConsoleColor.PURPLE));
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")) {
                    if (ExecuteFileManager.fileRepeat(userCommand[1])) {
                        console.printError("Найдена рекурсия по пути " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                }
                console.println(ConsoleColor.toColor("Выполнение команды " + userCommand[0], ConsoleColor.YELLOW));
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()) {
                    case ASK_OBJECT -> {
                        Person person;
                        try {
                            person = new PersonForm(console).build();
                            if (!person.validate()) throw new ExceptionInFileMode();
                        } catch (ExceptionInFileMode err) {
                            console.printError("Поля в файле не валидны! Объект не создан");
                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        person));
                        if (newResponse.getStatus() != ResponseStatus.OK) {
                            console.printError(newResponse.getResponse());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();
                    }
                    default -> {
                    }
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException) {
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");

        }
    }
}