package examples.data.forms;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.command.UserInput;
import examples.data.Location;
import examples.managers.InputManager;

/**
 * class for creating Location with some values
 */
public class LocationForm extends Form<Location> {
    private UserInput input = new InputManager();
    private Printable console;

    public LocationForm(Printable console) {
        this.console = console;
    }

    /**
     * add coordinate for x
     *
     * @return Double x
     * @throws IllegalArgumentException
     * @NotNull
     */
    public Double addX() throws IllegalArgumentException {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите координаты для оси " + ConsoleColor.PURPLE + "x" + ConsoleColor.RESET + ": ");
            String txtX = input.next();
            try {
                Double x = Double.parseDouble(txtX);
                return x;
            } catch (IllegalArgumentException e) {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например" + ConsoleColor.RESET + ": " + ConsoleColor.PURPLE + "1.0001");
            }
        }
    }

    /**
     * add coorddinate for y
     *
     * @return long y
     * @throws IllegalArgumentException
     * @NotNull
     */

    public long addY() throws IllegalArgumentException {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите координаты для оси " + ConsoleColor.PURPLE + "y" + ConsoleColor.RESET + ": ");
            String txtY = input.next();
            try {
                long y = Long.parseLong(txtY);
                return y;
            } catch (IllegalArgumentException e) {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например" + ConsoleColor.RESET + ": " + ConsoleColor.PURPLE + "10000000");
            }
        }
    }

    /**
     * add name for location
     *
     * @return String name
     * @throws IllegalArgumentException
     * @NotNull
     */
    public String addName() throws IllegalArgumentException {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите название " + ConsoleColor.PURPLE + "локации" + ConsoleColor.RESET + ": ");
            String nameLocation = input.next();
            if (nameLocation.isEmpty()) {
                console.printError("Простите, но название не может быть пустым!");
            } else return nameLocation;
        }
    }

    @Override
    public Location build() {
        Location location = new Location
                (
                        addX(),
                        addY(),
                        addName()
                );
        return location;
    }

}
