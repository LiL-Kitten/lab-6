package org.example.commands;


import org.example.data.Person;
import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.util.ConsoleColor;

import org.example.util.Printable;

import org.example.managers.CollectionManager;

import java.util.Objects;


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
    public Response execute(Request request) {
        if (!request.getArg().isBlank());
        if (Objects.isNull(request.getObj())){
            return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        } else{
            request.getObj().setID(CollectionManager.getNextID());
            collectionManager.addElement(request.getObj());
            return new Response(ResponseStatus.OK, "Объект успешно добавлен");
        }
    }
}

