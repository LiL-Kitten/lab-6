package examples.managers;

import examples.command.Printable;
import examples.command.UserInput;

import java.util.Scanner;


public class InputManager implements UserInput {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String next() {
        return scanner.nextLine().trim();
    }

}
