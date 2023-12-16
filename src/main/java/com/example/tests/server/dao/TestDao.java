package com.example.tests.server.dao;

import com.example.tests.server.entity.Test;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.mapper.TestMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TestDao {
    private static final String INSERT_TEST = "INSERT tests(name) VALUES(?);";
    private static final String SELECT_ALL = "SELECT * FROM tests WHERE is_deleted = false;";
    private static final String SELECT_BY_ID = "SELECT * FROM tests WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM tests WHERE name LIKE ? and is_deleted = false;";
    private static final String DELETE_BY_ID = "UPDATE tests SET is_deleted = true WHERE id = ?;";

    private final JdbcTemplate<Test> jdbcTemplate;

    public TestDao(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate<>(new TestMapper(), connection);
    }

    public int add(Test test) throws DaoException {
        return jdbcTemplate.executeUpdateInsertQuery(INSERT_TEST, test.getName());
    }

    public List<Test> getAll() throws DaoException {
        return jdbcTemplate.executeSelectQuery(SELECT_ALL);
    }

    public Optional<Test> findById(int id) throws DaoException {
        return jdbcTemplate.executeSelectQueryForSingleObject(SELECT_BY_ID, id);
    }

    public List<Test> searchByName(String query) throws DaoException {
        return jdbcTemplate.executeSelectQuery(SELECT_BY_NAME, "%" + query + "%");
    }

    public void delete(int id) throws DaoException {
        jdbcTemplate.executeUpdateInsertQuery(DELETE_BY_ID, id);
    }
}
