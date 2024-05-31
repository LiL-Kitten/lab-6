package org.example.commands;

import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.forms.PersonForm;
import org.example.util.*;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;


public class ExecuteScript extends Command {
    private Printable console;
    private Client client;
    private RuntimeManager run;
    private ExecuteFileManager executeFileManager;

    public ExecuteScript(Printable console, Client client, RuntimeManager run) {
        super("execute_script", "выполнение скрипта");
        this.console = console;
        this.client = client;
        this.run = run;
    }

    @Override
    public void execute(String args) {
        System.out.println(args);
        console.println("процесс выполнения начался");
        try {
            executeFileManager = new ExecuteFileManager(args);
        } catch (FileNotFoundException e) {
            console.println("Файл не найден: " + e.getMessage());
            return;
        } catch (IOException e) {
            console.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        while (executeFileManager.hasElements()) {
            run.processResponse( client.sendAndAskResponse(new Request(executeFileManager.getNextElement())));
        }
    }
}

class ExecuteFileManager {
    private Deque<String> lines = new ArrayDeque<>();

    public ExecuteFileManager(String relativePath) throws IOException {

        File file = new File(relativePath);


        if (!file.exists()) {
            throw new FileNotFoundException("Файл не существует: " + relativePath);
        }

        if (!file.canRead()) {
            throw new IOException("Файл недоступен для чтения: " + relativePath);
        }

        readAndProcessFile(file);
    }

    private void readAndProcessFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.trim().isEmpty()) {
                        lines.add(word.trim());
                    }
                }
            }
        }
    }

    public String getNextElement() {
        return lines.poll();
    }

    public boolean hasElements() {
        return !lines.isEmpty();
    }
}
