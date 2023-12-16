package com.example.tests.server.service;

import com.example.tests.server.dao.UserDao;
import com.example.tests.server.entity.User;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.exception.TransactionException;
import com.example.tests.server.pool.TransactionProvider;

import java.sql.Connection;
import java.util.Optional;

public class UserService {
    private final TransactionProvider transactionManager = TransactionProvider.getInstance();

    public Optional<User> login(String login, String password) {
        Optional<User> userOptional = Optional.empty();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDao userDao = new UserDao(connection);
            userOptional = userDao.findByLoginAndPassword(login, password);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
        } finally {
            transactionManager.endTransaction();
        }
        return userOptional;
    }

}
