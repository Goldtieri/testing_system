package com.example.tests.server.mapper;

import com.example.tests.server.entity.Option;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionMapper implements ObjectMapper<Option> {
    @Override
    public Option toObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String content = resultSet.getString("content");
        boolean isCorrect = resultSet.getBoolean("is_correct");
        int questionId = resultSet.getInt("question_id");
        return new Option(id, content, questionId, isCorrect);
    }
}
