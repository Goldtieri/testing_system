package com.example.tests.server.entity;

import java.io.Serial;
import java.io.Serializable;

public class TestResult implements Serializable, Identifiable {
    @Serial
    private static final long serialVersionUID = 8764803486357586301L;

    private final int correctAnswers;
    private final int totalAnswers;

    public TestResult(int correctAnswers, int totalAnswers) {
        this.correctAnswers = correctAnswers;
        this.totalAnswers = totalAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "correctAnswers=" + correctAnswers +
                ", totalAnswers=" + totalAnswers +
                '}';
    }
}
