package org.example.util;

import org.example.commands.ExecuteScript;
import org.example.commands.Exit;

import java.util.Scanner;


public class InputManager {

    private Scanner scanner = new Scanner(System.in);
    private String command;
    private String argument;

    public InputManager(){}

    public void commandInput(){
        String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
        command = userCommand[0];
        argument = userCommand[1];
    }

    public String getCommand() {
        return command;
    }
    public String getArgument() {
        return argument;
    }

}
