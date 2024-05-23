package org.example.command.commands;

import org.example.dth.Request;

/**
 * Interface for executing any commands
 */
public interface Executable {
    public String execute(Request request);
}
