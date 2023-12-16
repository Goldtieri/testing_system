package com.example.tests.server.mapper;

import com.example.tests.server.entity.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper implements ObjectMapper<Test> {
    @Override
    public Test toObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Test(id, name);
    }
}
