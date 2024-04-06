package examples.command.commands;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.managers.FileManager;

/**
 * save collection in file
 */
public class Save extends Command
{
    private Console console;
    private FileManager fileManager;
    public Save(Console console, FileManager fileManager)
    {
        super("save", ": сохранить коллекцию в файл");
        this.console = console;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String a) throws IllegalArgumentException
    {
        if (!a.isBlank()) throw new IllegalArgumentException();
        fileManager.save();
        console.println(ConsoleColor.GREEN + "супер, все сохранилось в текущий файлик =)");
    }
}