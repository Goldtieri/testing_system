package com.example.tests.server.pool;

import com.example.tests.server.exception.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionProvider {
    private static final Logger LOGGER = LogManager.getLogger(TransactionProvider.class);
    private static TransactionProvider instance;
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    private TransactionProvider() {
    }

    public static TransactionProvider getInstance() {
        if (instance == null) {
            instance = new TransactionProvider();
        }
        return instance;
    }

    public void initializeTransaction() throws TransactionException {
        if (connectionThreadLocal.get() == null) {
            try {
                Connection connection = ConnectionPool.getInstance().getConnection();
                if (connection == null) {
                    throw new TransactionException("Current thread was interrupted caused by null connection");
                }
                connectionThreadLocal.set(connection);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Failed to set connection autocommit false.", e);
            }
        }
    }

    public Connection getConnection() throws TransactionException {
        Connection connection = connectionThreadLocal.get();
        if (connection == null) {
            throw new TransactionException("Can't get connection.");
        }
        return connection;
    }

    public void endTransaction() {
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            try {
                connectionThreadLocal.remove();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("Failed to set connection autocommit true.", e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("Failed to close connection.", e);
                }
            }
        }
    }

    public void commit() throws TransactionException {
        Connection connection = connectionThreadLocal.get();
        if (connection == null) {
            throw new TransactionException("Can't get connection.");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Failed to commit connection.", e);
            throw new TransactionException("Failed to commit connection.", e);
        }
    }

    public void rollback() {
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Failed to rollback connection.", e);
            }
        }

    }

}
