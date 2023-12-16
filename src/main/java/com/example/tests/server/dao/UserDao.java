package com.example.tests.server.dao;

import com.example.tests.server.entity.User;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.mapper.UserMapper;

import java.sql.Connection;
import java.util.Optional;

public class UserDao {
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = """
            SELECT * FROM users WHERE login = ? AND password = ?;
            """;

    private final JdbcTemplate<User> jdbcTemplate;

    public UserDao(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate<User>(new UserMapper(), connection);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        return jdbcTemplate.executeSelectQueryForSingleObject(SELECT_BY_LOGIN_AND_PASSWORD, login, password);
    }
}
