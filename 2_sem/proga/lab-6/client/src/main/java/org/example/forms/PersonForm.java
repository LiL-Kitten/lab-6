package org.example.forms;

import org.example.data.*;
import org.example.util.ConsoleColor;
import org.example.util.InputManager;
import org.example.util.Printable;
import org.example.util.UserInput;

/**
 * class for creating object Person and add some values from other forms
 */import java.util.Scanner;

public class PersonForm extends Form { // Предположим, что здесь есть интерфейс
    private Printable console;
    private static Scanner input = new Scanner(System.in);

    public PersonForm(Printable console) {
        this.console = console;
    }

    @Override
    public Person build() {
        return new Person(
                addName(),
                addCoordinates(),
                addHeight(),
                addPassportID(),
                addColorHair(),
                addNationality(),
                addLocation()
        );
    }

    public Coordinates addCoordinates() {
        CoordinatesForm coordinates = new CoordinatesForm(console);
        return coordinates.build();
    }

    public Location addLocation() {
        LocationForm location = new LocationForm(console);
        return location.build();
    }

    public String addName() {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите имя" + ConsoleColor.RESET + ": ");
            String name = input.nextLine().trim();
            if (name.isEmpty()) {
                console.printError("Простите, но название не может быть пустым!");
            } else {
                return name;
            }
        }
    }

    public Float addHeight() {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите вес" + ConsoleColor.RESET + ": ");
            String txtHeight = input.nextLine().trim();
            try {
                float height = Float.parseFloat(txtHeight);
                if (height < 0) {
                    console.printError("Вес не может быть отрицательным!");
                } else {
                    return height;
                }
            } catch (NumberFormatException e) {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например" + ConsoleColor.RESET + ": " + ConsoleColor.PURPLE + "1.023");
            }
        }
    }

    public String addPassportID() {
        console.print(ConsoleColor.GREEN + "Введите" + ConsoleColor.PURPLE + " passport ID" + ConsoleColor.RESET + ": ");
        return input.nextLine().trim();
    }

    public Color addColorHair() throws IllegalArgumentException {
        while (true) {
            console.print(Color.colors() + ConsoleColor.GREEN + "Выберете один из указанных ранее цветов" + ConsoleColor.RESET + ": ");
            String txt =
input.nextLine().trim().toUpperCase();
        try {
        return Color.valueOf(txt);
        } catch (IllegalArgumentException e) {
        console.printError("Вы указали что-то не из списка =(");
        }
        }
        }

public Country addNationality() throws IllegalArgumentException {
        while (true) {
        console.print(ConsoleColor.CYAN + Country.countries() + ConsoleColor.GREEN + "Введите одну из указанных ранее " + ConsoleColor.PURPLE + "национальностей" + ConsoleColor.RESET + ": ");
        String countryInput = input.nextLine().trim().toUpperCase();
        try {
        return Country.valueOf(countryInput);
        } catch (IllegalArgumentException e) {
        console.printError("Вы указали что-то не из списка =(");
        }
        }
        }
        }


