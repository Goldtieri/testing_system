package com.example.tests.client.service;

import com.example.tests.client.TcpClient;
import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final TcpClient tcpClient = TcpClient.getInstance();

    public List<Test> getTests() throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("GET_TESTS");
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        @SuppressWarnings("unchecked")
        List<Test> tests = (ArrayList<Test>) tcpResponse.getAttribute("tests");
        return tests;
    }

    public List<Question> getQuestions(int testId) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("GET_QUESTIONS");
        tcpRequest.addParameter("test_id", testId);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        @SuppressWarnings("unchecked")
        List<Question> questions = (ArrayList<Question>) tcpResponse.getAttribute("questions");
        return questions;
    }

    public List<Option> getOptions(int questionId) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("GET_OPTIONS");
        tcpRequest.addParameter("question_id", questionId);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        @SuppressWarnings("unchecked")
        List<Option> options = (ArrayList<Option>) tcpResponse.getAttribute("options");
        return options;
    }

    public Test getTestById(int testId) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("GET_TEST_BY_ID");
        tcpRequest.addParameter("id", testId);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        @SuppressWarnings("unchecked")
        Test test = (Test) tcpResponse.getAttribute("test");
        return test;
    }

    public void uploadTest(Test test) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("UPLOAD_TEST");
        tcpRequest.addParameter("test", test);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
    }

    public TestResult uploadAnswers(List<String> answers) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("GET_RESULT");
        tcpRequest.addParameter("answers", answers);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        return (TestResult) tcpResponse.getAttribute("result");
    }

    public List<Test> search(String query) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("SEARCH");
        tcpRequest.addParameter("query", query);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        @SuppressWarnings("unchecked")
        List<Test> tests = (ArrayList<Test>) tcpResponse.getAttribute("tests");
        return tests;
    }

    public User login(String login, String password) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("LOGIN");
        tcpRequest.addParameter("login", login);
        tcpRequest.addParameter("password", password);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
        return (User) tcpResponse.getAttribute("user");
    }

    public void deleteTest(int id) throws IOException, ClassNotFoundException {
        tcpClient.connect();
        TcpRequest tcpRequest = new TcpRequest("DELETE_TEST");
        tcpRequest.addParameter("id", id);
        TcpResponse tcpResponse = tcpClient.sendRequest(tcpRequest);
        tcpClient.disconnect();
    }

}
