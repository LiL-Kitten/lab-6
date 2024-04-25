package examples.command.commands;


import examples.command.ConsoleColor;

import examples.command.Printable;
import examples.exceptions.InvalidForm;

import examples.managers.CollectionManager;

import examples.data.forms.PersonForm;

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
     * @param a An empty string is passed for this command
     */
    @Override
    public void execute(String a) throws IllegalArgumentException {

        if (!a.isBlank()) throw new IllegalArgumentException("Повторите ввод");
        try {
            console.println(ConsoleColor.toColor("Создание объекта StudyGroup", ConsoleColor.PURPLE));
            collectionManager.addElement(new PersonForm(console).build());
            console.println(ConsoleColor.toColor("Создание объекта StudyGroup окончено успешно!", ConsoleColor.PURPLE));
        } catch (InvalidForm invalidForm) {
            console.printError("Поля объекта не валидны! Объект не создан!");
        }
    }
}

