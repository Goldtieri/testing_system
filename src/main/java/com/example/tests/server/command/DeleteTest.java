package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.service.TestService;

public class DeleteTest implements Command{
    private final TestService testService;

    public DeleteTest(TestService testService) {
        this.testService = testService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        int id = (int) tcpRequest.getParameter("id");
        testService.deleteTest(id);
        return null;
    }
}
