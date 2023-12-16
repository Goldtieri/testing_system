package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Test;
import com.example.tests.server.service.TestService;

import java.util.List;

public class GetTests implements Command {
    private final TestService testService;

    public GetTests(TestService testService) {
        this.testService = testService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        List<Test> tests = testService.getAll();
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("tests", tests);
        return tcpResponse;
    }
}
