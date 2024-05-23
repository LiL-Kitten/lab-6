package org.example.util;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;
import org.example.exceptions.CommandRuntimeError;
import org.example.exceptions.ExitObliged;
import org.example.exceptions.NoSuchCommand;
import org.example.managers.CommandManager;
import org.example.managers.RuntimeManager;

import java.io.IOException;

public class RequestHandler {
    private RuntimeManager runtimeManager;

    public RequestHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }

    public Response handle(Request request) {
        try {
            return runtimeManager.interactiveMode(request);
        } catch (RuntimeException e) {
            return new Response(ResponseStatus.ERROR, "Ошибка выполнения: " + e.getMessage());
        }
    }
}