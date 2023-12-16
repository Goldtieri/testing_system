package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;

public interface Command {
    TcpResponse execute(TcpRequest tcpRequest);
}
