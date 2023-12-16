package com.example.tests.server.controller.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TcpRequest implements Serializable {
    public static final long serialVersionUID = 8421676314988954472L;

    private final String command;
    private final Map<String, Object> parameters;

    public TcpRequest(String command) {
        this.command = command;
        parameters = new HashMap<>();
    }

    public String getCommand() {
        return command;
    }

    public void addParameter(String key, Object value) {
        parameters.put(key, value);
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcpRequest that = (TcpRequest) o;
        return Objects.equals(command, that.command) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, parameters);
    }
}
