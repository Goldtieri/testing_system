package com.example.tests.server.mapper;

import com.example.tests.server.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements ObjectMapper<Question> {
    @Override
    public Question toObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String content = resultSet.getString("content");
        int testId = resultSet.getInt("test_id");
        return new Question(id, content, testId);
    }
}
