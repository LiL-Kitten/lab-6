package org.example.util;

import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.exceptions.ExceptionInFileMode;
import org.example.exceptions.ExitObliged;
import org.example.forms.PersonForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class RuntimeManager {
    private static Printable console;
    private ScannerManager userScanner;
    private Client client;

    public RuntimeManager(Printable console, Client client, ScannerManager scannerManager) {
        this.console = console;
        this.client = client;
        this.userScanner = scannerManager;
    }

    public void interactiveMode() throws UnknownHostException {

        client.createSocket();

        while (true) {
            try {
                if (!userScanner.getInput().hasNext()) {
                    System.exit(1);
                }

                String[] userCommand = (userScanner.getInput().nextLine().trim() + " ").split(" ", 2); // прибавляем пробел, чтобы split выдал два элемента в массиве
                Response response = null;
                    response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));


                // Печать ответа только если он не равен null
                if (response != null) {
                    this.printResponse(response);

                    // Интерпретация или выполнение в зависимости от контекста
                    if (response.requiresObject()) {
                        Person person = new PersonForm(console).build();
                        if (!person.validate()) {
                            console.printError("Объект некорректен!");
                            continue;
                        }

                        Response newResponse = null;

                            newResponse = client.sendAndAskResponse(
                                    new Request(
                                            userCommand[0].trim(),
                                            person));


                        if (newResponse != null) {
                            this.printResponse(newResponse);
                        }
                    }

                    // Выполнение скрипта, если команда - execute_script
                    if ("execute_script".equalsIgnoreCase(userCommand[0].trim())) {
                        Console.setFileMode(true);
                        this.fileExecution(response.getResponse());
                        Console.setFileMode(false);
                    }
                }

            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            }
        }
    }


    private void printResponse(Response response){
        if (response == null) {
            console.printError("Не удалось получить ответ от сервера.");
            return;
        }
        console.println("Получен ответ с статусом: " + response.getStatus());

        switch (response.getStatus()) {
            case OK -> {
                if (Objects.isNull(response.getCollection())) {
                    console.println(response.getResponse());
                } else {
                    console.println(response.getResponse() + "\n" + response.getCollection().toString());
                }
            }
            case ERROR -> console.printError(response.getResponse());
            case WRONG_ARGUMENTS -> console.printError("Неверное использование команды!");
            case ASK_OBJECT -> {
                Person person;
                try {
                    person = new PersonForm(console).build();
                    if (!person.validate()) throw new ExceptionInFileMode();
                } catch (ExceptionInFileMode err) {
                    console.printError("Поля в файле не валидны! Объект не создан");
                }
            }
            default -> console.printError("Неизвестный статус ответа!");
        }
    }
    private void fileExecution(String args) {
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        }
        else console.println(ConsoleColor.toColor("Путь получен успешно", ConsoleColor.PURPLE));
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")){
                    if(ExecuteFileManager.fileRepeat(userCommand[1])){
                        console.printError("Найдена рекурсия по пути " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                }
                console.println(ConsoleColor.toColor("Выполнение команды " + userCommand[0], ConsoleColor.YELLOW));
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()){
                    case ASK_OBJECT -> {
                        Person person;
                        try{
                            person = new PersonForm(console).build();
                            if (!person.validate()) throw new ExceptionInFileMode();
                        } catch (ExceptionInFileMode err){
                            console.printError("Поля в файле не валидны! Объект не создан");
                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        person));
                        if (newResponse.getStatus() != ResponseStatus.OK){
                            console.printError(newResponse.getResponse());
                        }
                        else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitObliged();
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();
                    }
                    default -> {}
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException){
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        } catch (ExitObliged e) {
            throw new RuntimeException(e);
        }
    }
}