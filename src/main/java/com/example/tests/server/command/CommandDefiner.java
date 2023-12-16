package com.example.tests.server.command;

import com.example.tests.server.service.OptionService;
import com.example.tests.server.service.QuestionService;
import com.example.tests.server.service.TestService;
import com.example.tests.server.service.UserService;

public class CommandDefiner {
    public static Command define(String command) {
        return switch (command) {
            case "GET_TESTS" -> new GetTests(new TestService());
            case "GET_TEST_BY_ID" -> new GetTestById(new TestService());
            case "GET_QUESTIONS" -> new GetQuestions(new QuestionService());
            case "GET_OPTIONS" -> new GetOptions(new OptionService());
            case "UPLOAD_TEST" -> new UploadTest(new TestService());
            case "GET_RESULT" -> new GetResult(new OptionService());
            case "SEARCH" -> new SearchTests(new TestService());
            case "LOGIN" -> new Login(new UserService());
            case "DELETE_TEST" -> new DeleteTest(new TestService());
            default -> throw new IllegalArgumentException("Can't define command.");
        };
    }
}
