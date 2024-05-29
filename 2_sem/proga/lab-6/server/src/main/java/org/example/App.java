package org.example;

import org.example.exceptions.ExitObliged;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.FileManager;
import org.example.managers.RuntimeManager;
import org.example.util.Console;
import org.example.util.RequestHandler;
import org.example.util.Server;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ExitObliged {
        Console console = new Console();
        RuntimeManager runtimeManager = new RuntimeManager(console);
        RequestHandler requestHandler = new RequestHandler(runtimeManager);
        Server server = new Server(6087, requestHandler);
        server.run();
    }
}