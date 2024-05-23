package org.example.util;

import java.util.Scanner;


public class InputManager implements UserInput {
    private Scanner scanner = new Scanner(System.in);


    @Override
    public String nextLine() {
        return scanner.nextLine().trim();
    }

    @Override
    public String next() {
        return scanner.nextLine().trim();
    }

}
