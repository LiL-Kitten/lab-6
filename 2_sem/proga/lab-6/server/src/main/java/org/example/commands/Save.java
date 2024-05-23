package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.managers.CollectionManager;
import org.example.managers.FileManager;

/**
 * save collection in file
 */
public class Save extends Command {
    private Printable console;
    private FileManager fileManager;
    private CollectionManager collectionManager;

    public Save(Printable console, FileManager fileManager, CollectionManager collectionManager) {
        super("save", ": сохранить коллекцию в файл");
        this.console = console;
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        fileManager.save();
        collectionManager.setLastSaveDate();
        console.println(ConsoleColor.GREEN + "супер, все сохранилось в текущий файлик =)");
        return new Response(ResponseStatus.OK,ConsoleColor.GREEN + "супер, все сохранилось в текущий файлик =)");
    }
}