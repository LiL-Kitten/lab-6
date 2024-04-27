package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.exceptions.ExitObliged;
import examples.managers.CollectionManager;
import examples.managers.FileManager;

import java.io.IOException;

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
    public void execute(String a) throws ExitObliged, IOException {
        fileManager.save();
        collectionManager.setLastSaveDate();
        console.println(ConsoleColor.GREEN + "супер, все сохранилось в текущий файлик =)");
    }
}