package org.example.command.commands;


import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.util.ConsoleColor;

import org.example.util.Printable;

import org.example.managers.CollectionManager;


/**
 * Add a new item to the collection
 */

public class Add extends Command {
    private Printable console;
    private CollectionManager collectionManager;


    public Add(Printable console, CollectionManager collectionManager) {
        super("add", " {element} добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * performs adding a new element to the collection
     *
     * @param request An empty string is passed for this command
     */
    @Override
    public String execute(Request request) {

        console.println(ConsoleColor.toColor("Создание объекта StudyGroup", ConsoleColor.PURPLE));
        collectionManager.addElement((Person)(request.getObj()));
        return ConsoleColor.toColor("Создание объекта StudyGroup окончено успешно!", ConsoleColor.PURPLE);
    }
}

