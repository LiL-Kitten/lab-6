package org.example.commands;

import org.example.dth.Request;
import org.example.util.Client;
import org.example.util.Printable;
import org.example.util.RuntimeManager;

public class Exit extends  Command{
    private Client client;
    private Printable console;
    private RuntimeManager run;


    public Exit(Printable console, Client client, RuntimeManager run) {
        super("exit", "exit");
        this.console = console;
        this.client = client;
        this.run = run;
    }


    @Override
    public void execute(String args) {
        run.processResponse(client.sendAndAskResponse(new Request("exit")));
        client.closeConnection();
        System.exit(1);
    }
}
