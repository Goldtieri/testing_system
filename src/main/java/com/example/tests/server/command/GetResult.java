package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.TestResult;
import com.example.tests.server.service.OptionService;

import java.util.List;

public class GetResult implements Command {
    private final OptionService optionService;

    public GetResult(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        @SuppressWarnings("unchecked")
        List<String> answers = (List<String>) tcpRequest.getParameter("answers");
        TestResult testResult = optionService.getResult(answers);
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("result", testResult);
        return tcpResponse;
    }
}
