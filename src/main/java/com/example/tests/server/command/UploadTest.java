package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Test;
import com.example.tests.server.service.TestService;

public class UploadTest implements Command {
    private final TestService testService;

    public UploadTest(TestService testService) {
        this.testService = testService;
    }


    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        Test test = (Test) tcpRequest.getParameter("test");
        testService.upload(test);
        return new TcpResponse();
    }
}
