package org.example;

import org.example.util.Client;
import org.example.util.Console;
import org.example.util.RuntimeManager;
import org.example.util.ScannerManager;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        Console console = new Console();
        ScannerManager scannerManager = new ScannerManager();
        Client client = new Client(console, scannerManager);
        RuntimeManager runtime = new RuntimeManager(console, client, scannerManager);
            runtime.interactiveMode();

    }
}