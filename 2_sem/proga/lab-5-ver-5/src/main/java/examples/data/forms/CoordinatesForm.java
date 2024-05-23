package examples.data.forms;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.command.UserInput;
import examples.managers.InputManager;
import examples.data.Coordinates;

/**
 * class for creating examples Coordinates and add some value
 */
public class CoordinatesForm extends Form<Coordinates> {
    /**
     * users input
     *
     * @param input
     */
    private UserInput input = new InputManager();

    /**
     * output in console
     *
     * @param console
     */
    private Printable console;
    private Coordinates coordinates;

    public CoordinatesForm(Printable console) {
        this.console = console;
    }

    /**
     * add coordinates for x
     *
     * @return Integer x
     * @throws IllegalArgumentException
     * @NotNull
     */
    public Integer addX() throws IllegalArgumentException {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите координаты для оси " + ConsoleColor.PURPLE + "x" + ConsoleColor.RESET + ": ");
            String txtX = input.next();
            try {
                Integer x = Integer.parseInt(txtX);
                return x;
            } catch (NumberFormatException e) {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например: " + ConsoleColor.PURPLE + "1554" + ConsoleColor.RESET);

            }
        }
    }

    /**
     * add coordinates for y
     *
     * @return double y
     * @throws IllegalArgumentException
     * @NotNull
     */
    public double addY() throws IllegalArgumentException {
        while (true) {
            console.print(ConsoleColor.GREEN + "Введите координаты для оси " + ConsoleColor.PURPLE + "y" + ConsoleColor.RESET + ": ");
            String txtY = input.next();
            try {
                double y = Double.parseDouble(txtY);
                return y;
            } catch (IllegalArgumentException e) {
                console.printError("Необходимо ввести число!!!");
                console.println(ConsoleColor.RED + "Например" + ConsoleColor.RESET + ": " + ConsoleColor.PURPLE + "2.0");
            }
        }
    }

    /**
     * create object Coordinates
     *
     * @return Coordinates
     */
    @Override
    public Coordinates build() {
        Coordinates coordinates = new Coordinates(addX(), addY());
        return coordinates;
    }
}
