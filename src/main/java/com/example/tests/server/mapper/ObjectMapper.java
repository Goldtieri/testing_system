package com.example.tests.server.mapper;

import com.example.tests.server.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T extends Identifiable> {
    T toObject(ResultSet resultSet) throws SQLException;
}
