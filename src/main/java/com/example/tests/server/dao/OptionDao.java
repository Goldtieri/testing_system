package com.example.tests.server.dao;

import com.example.tests.server.entity.Option;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.mapper.OptionMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OptionDao {
    private static final String INSERT_OPTION = "INSERT INTO options(content, question_id, is_correct) VALUES(?, ?, ?);";
    private static final String SELECT_ALL_BY_QUESTION_ID = "select * from options where question_id = ?";
    private static final String IS_OPTION_CORRECT = "SELECT is_correct FROM options WHERE id = ?;";

    private final JdbcTemplate<Option> jdbcTemplate;


    public OptionDao(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate<>(new OptionMapper(), connection);
    }

    public void add(Option option, int questionId) throws DaoException {
        jdbcTemplate.executeUpdateInsertQuery(INSERT_OPTION, option.getContent(), questionId, option.isCorrect());
    }

    public List<Option> findAllByQuestionId(int questionId) throws DaoException {
        return jdbcTemplate.executeSelectQuery(SELECT_ALL_BY_QUESTION_ID, questionId);
    }

    public boolean isCorrect(int id) throws DaoException {
        return jdbcTemplate.executeBooleanQuery(IS_OPTION_CORRECT, id);
    }
}
