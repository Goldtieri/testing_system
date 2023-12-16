package com.example.tests.server.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Question implements Identifiable, Serializable {
    public static final long serialVersionUID = -4477469515472008166L;
    private int id;
    private String content;
    int testId;
    List<Option> options;

    /**
     * For reading.
     */
    public Question(int id, String content, int test_id) {
        this.id = id;
        this.content = content;
    }

    /**
     * For writing.
     */
    public Question(String content, List<Option> options) {
        this.content = content;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id && testId == question.testId && Objects.equals(content, question.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, testId);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", testId=" + testId +
                '}';
    }
}
