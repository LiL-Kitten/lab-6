package examples.data.forms;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.command.UserInput;
import examples.data.*;
import examples.exceptions.InvalidForm;
import examples.managers.InputManager;


/**
 * class for creating object Person and add some values from other forms
 */
public class PersonForm extends Form<Person>
{
    private UserInput input = new InputManager();
    private Person person;
    private LocationForm location;
    private CoordinatesForm coordinates;
    private Printable console;

    public PersonForm(Printable console)
    {
        this.console = console;
    }
    @Override
    public Person build() throws InvalidForm
    {
        return person = new Person
                (
                        addName(),
                        addCoordinates(),
                        addHeight(),
                        addPassportID(),
                        addColorHair(),
                        addNationality(),
                        addLocation()
                );
    }

    public Coordinates addCoordinates()
    {
        coordinates = new CoordinatesForm(console);
        return coordinates.build();
    }

    public Location addLocation()
    {
        location = new LocationForm(console);
        return location.build();
    }

    public String addName()
    {
        while (true)
        {
            console.print(ConsoleColor.GREEN + "Введите имя" + ConsoleColor.RESET + ": ");
            String name=input.next();
            if(name.isEmpty()) {console.printError("Простите, но название не может быть пустым!");}
            else return name;
        }
    }

    /**
     * add weight
     * @return Float weight
     */
    public Float addHeight()
    {
        while (true)
        {
            console.print(ConsoleColor.GREEN + "Введите вес" + ConsoleColor.RESET + ": ");
            String txtHeight = input.next();
            try
            {
                float height = Float.parseFloat(txtHeight);
                if (height < 0) {console.printError("Вес не может быть отрицательным!");}
                else {return height;}
            }
            catch (NumberFormatException e)
            {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например" + ConsoleColor.RESET + ": " + ConsoleColor.PURPLE + "1.023");
            }
        }
    }

    public String addPassportID()
    {
        console.print(ConsoleColor.GREEN + "Введите" + ConsoleColor.PURPLE + " passport ID" + ConsoleColor.RESET + ": ");
        return input.next();
    }


    public Color addColorHair() throws IllegalArgumentException
    {
        while(true)
        {
            console.print(Color.colors() + ConsoleColor.GREEN + "Выберете один из указанных ранее цветов" + ConsoleColor.RESET + ": ");
            String txt = input.next().toUpperCase();
            try
            {
                Color color = Color.valueOf(txt);
                return color;
            } catch (IllegalArgumentException e)
            {console.printError("Вы указали что то не из списка =(");}
        }
    }

    public Country addNationality() throws IllegalArgumentException
    {
        while (true)
        {
            console.print(ConsoleColor.CYAN + Country.countries() + ConsoleColor.GREEN + "Введите одну из указанных ранее " + ConsoleColor.PURPLE + "национальностей" + ConsoleColor.RESET + ": ");
            String countryInput = input.next().toUpperCase();
            try
            {
                Country country = Country.valueOf(countryInput);
                return country;
            }
            catch (IllegalArgumentException e) {console.printError("Вы указали что то не из списка =(");}
        }
    }


}



