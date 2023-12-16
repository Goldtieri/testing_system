package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Option;
import com.example.tests.server.service.OptionService;

import java.util.List;

public class GetOptions implements Command {
    private final OptionService optionService;

    public GetOptions(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        int questionId = (int) tcpRequest.getParameter("question_id");
        List<Option> options = optionService.findByQuestionId(questionId);
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("options", options);
        return tcpResponse;
    }
}
