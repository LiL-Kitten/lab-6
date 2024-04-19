package examples.managers;

import examples.command.Printable;
import examples.command.UserInput;

import java.util.Scanner;


public class InputManager implements UserInput {
    private Scanner scanner = new Scanner(System.in);
    private Printable console;

    @Override
    public String next() {
        String input = scanner.nextLine().trim();
        return input;
    }

}
