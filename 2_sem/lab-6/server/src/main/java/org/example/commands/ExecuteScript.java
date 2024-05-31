package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.Printable;

import org.example.managers.ExecuteFileManager;
import org.example.util.ConsoleColor;
import org.example.exceptions.CommandRuntimeError;
import org.example.exceptions.ExitObliged;
import org.example.exceptions.NoSuchCommand;
import org.example.managers.CommandManager;
import org.example.managers.FileManager;

import java.io.*;
import java.util.NoSuchElementException;

/**
 * One of the most steamy commands, necessary to read and execute a script from the specified file
 * The script contains commands in the same form in which the user enters them in interactive mode.
 */
public class ExecuteScript extends Command {
    private Printable console;
    private CommandManager commandManager;
    private FileManager fileManager;
    private ExecuteFileManager executeFileManager;

    public ExecuteScript(Printable console, FileManager fileManager, CommandManager commandManager) {
        super("execute_script", ": считать и исполнить скрипт из указанного файла");
        this.console = console;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
            return  new Response(ResponseStatus.OK,"Рекурсия скрипта выполнялась");
    }

}
