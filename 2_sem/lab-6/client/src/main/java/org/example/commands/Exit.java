package org.example.commands;

import org.example.dth.Request;
import org.example.util.Client;
import org.example.util.Printable;

public class Exit extends  Command{
    private Client client;
    private Printable console;


    public Exit(Printable console, Client client) {
        super("exit", "exit");
        this.console = console;
        this.client = client;
    }


    @Override
    public void execute(String args) {
        console.printError("Дотвидания!");
        client.closeConnection();
        System.exit(1);
    }
}
