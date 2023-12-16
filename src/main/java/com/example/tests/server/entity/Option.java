package com.example.tests.server.entity;

import java.io.Serializable;
import java.util.Objects;

public class Option implements Identifiable, Serializable {
    public static final long serialVersionUID = -7449599236497020099L;

    private int id;
    private String content;
    private int questionId;
    private boolean isCorrect;

    public Option(int id, String content, int questionId, boolean isCorrect) {
        this.id = id;
        this.content = content;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public Option(String content, int questionId, boolean isCorrect) {
        this.content = content;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public Option(String content, boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Option option = (Option) o;
        return id == option.id && questionId == option.questionId && isCorrect == option.isCorrect
                && Objects.equals(content, option.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, questionId, isCorrect);
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", questionId=" + questionId +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
