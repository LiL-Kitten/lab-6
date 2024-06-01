package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.managers.CollectionManager;
import org.example.managers.FileManager;
import org.example.util.Console;

public class Exit extends Command {
    private FileManager fileManager;
    private CollectionManager collectionManager;
    Console console = new Console();
    public Exit(FileManager fileManager, CollectionManager collectionManager) {
        super("exit", ": Завершение программы!");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        fileManager.save();
        collectionManager.setLastSaveDate();
        console.println("Дотвидания =( \n все сохранилось))");
        return new Response(ResponseStatus.EXIT,"все сохранилось))");
    }
}