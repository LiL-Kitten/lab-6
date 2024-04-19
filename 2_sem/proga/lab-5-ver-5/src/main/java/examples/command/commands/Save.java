package examples.command.commands;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.exceptions.ExitObliged;
import examples.managers.FileManager;

/**
 * save collection in file
 */
public class Save extends Command {
    private Printable console;
    private FileManager fileManager;

    public Save(Printable console, FileManager fileManager) {
        super("save", ": сохранить коллекцию в файл");
        this.console = console;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String a) throws ExitObliged {
        fileManager.save();
        console.println(ConsoleColor.GREEN + "супер, все сохранилось в текущий файлик =)");
    }
}