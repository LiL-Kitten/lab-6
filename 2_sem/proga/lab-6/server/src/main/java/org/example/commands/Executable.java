package org.example.commands;

import org.example.dth.Request;
import org.example.dth.Response;

/**
 * Interface for executing any commands
 */
public interface Executable {
    public Response execute(Request request);
}
