package com.example.tests.client.session;

import com.example.tests.server.entity.Test;

import java.util.List;

public class TestsSession {
    private static TestsSession instance;
    private List<Test> tests;

    private TestsSession() {
    }

    public static TestsSession getInstance() {
        if (instance == null) {
            instance = new TestsSession();
        }
        return instance;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
