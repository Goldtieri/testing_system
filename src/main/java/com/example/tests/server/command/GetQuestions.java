package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.Question;
import com.example.tests.server.service.QuestionService;

import java.util.List;

public class GetQuestions implements Command {
    private final QuestionService questionService;

    public GetQuestions(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        int testId = (int) tcpRequest.getParameter("test_id");
        List<Question> questions = questionService.findByTestId(testId);
        TcpResponse response = new TcpResponse();
        response.addAttribute("questions", questions);
        return response;
    }
}
