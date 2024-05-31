package org.example;

import org.example.util.Client;
import org.example.util.Console;
import org.example.util.InputManager;
import org.example.util.RuntimeManager;

import java.net.SocketException;
import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        Console console = new Console();
        InputManager manager = new InputManager();
        Client client = new Client(console, manager);
        RuntimeManager runtimeManager = new RuntimeManager(console, client, manager );

        runtimeManager.run();
//        RuntimeManager runtime = new RuntimeManager(console, client, scannerManager);
//            runtime.interactiveMode();

    }
}