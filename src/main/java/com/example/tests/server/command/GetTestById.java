package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Test;
import com.example.tests.server.service.TestService;

import java.util.Optional;

public class GetTestById implements Command {
    private final TestService testService;

    public GetTestById(TestService testService) {
        this.testService = testService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        int id = (int) tcpRequest.getParameter("id");
        Optional<Test> test = testService.findById(id);
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("test",test.get());
        return tcpResponse;
    }
}
