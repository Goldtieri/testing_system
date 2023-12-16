package com.example.tests.server.dao;

import com.example.tests.server.entity.Identifiable;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.mapper.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate<T extends Identifiable> {
    private final ObjectMapper<T> objectMapper;
    private final Connection connection;

    public JdbcTemplate(ObjectMapper<T> objectMapper, Connection connection) {
        this.objectMapper = objectMapper;
        this.connection = connection;
    }

    public List<T> executeSelectQuery(String query, Object... parameters) throws DaoException {
        List<T> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = objectMapper.toObject(resultSet);
                list.add(object);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return list;
    }

    public int executeUpdateInsertQuery(String query, Object... parameters) throws DaoException {
        int generatedId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParametersInPreparedStatement(preparedStatement, parameters);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return generatedId;
    }

    public Optional<T> executeSelectQueryForSingleObject(String query, Object... parameters) throws DaoException {
        Optional<T> result = Optional.empty();
        List<T> list = executeSelectQuery(query, parameters);
        if (!list.isEmpty()) {
            result = Optional.of(list.get(0));
        }
        return result;
    }

    public boolean executeBooleanQuery(String query, Object... parameters) throws DaoException {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result =  resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    private void setParametersInPreparedStatement(PreparedStatement statement, Object... parameters) throws
            SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }
}
