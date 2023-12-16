package com.example.tests.server.mapper;

import com.example.tests.server.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User toObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        boolean isAdmin = resultSet.getBoolean("is_admin");
        return new User(id, login, isAdmin);
    }
}
