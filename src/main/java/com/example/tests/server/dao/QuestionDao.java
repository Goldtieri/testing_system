package com.example.tests.server.dao;

import com.example.tests.server.entity.Question;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.mapper.QuestionMapper;

import java.sql.Connection;
import java.util.List;

public class QuestionDao {
    private static final String INSERT_QUESTION = "INSERT questions(content, test_id) VALUES (?, ?);";
    private static final String SELECT_ALL_BY_TEST_ID = "SELECT * FROM questions WHERE test_id = ?";

    private final JdbcTemplate<Question> jdbcTemplate;

    public QuestionDao(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate<>(new QuestionMapper(), connection);
    }

    public int add(Question question) throws DaoException {
        return jdbcTemplate.executeUpdateInsertQuery(INSERT_QUESTION, question.getContent(), question.getTestId());
    }

    public List<Question> findAllByTestId(int testId) throws DaoException {
        return jdbcTemplate.executeSelectQuery(SELECT_ALL_BY_TEST_ID, testId);
    }

}
