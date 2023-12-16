package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Test;
import com.example.tests.server.service.TestService;

import java.util.List;

public class SearchTests implements Command {
    private final TestService testService;

    public SearchTests(TestService testService) {
        this.testService = testService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        String query = (String) tcpRequest.getParameter("query");
        List<Test> tests = testService.search(query);
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("tests", tests);
        return tcpResponse;
    }
}
