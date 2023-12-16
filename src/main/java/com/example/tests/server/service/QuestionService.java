package com.example.tests.server.service;

import com.example.tests.server.dao.QuestionDao;
import com.example.tests.server.entity.Question;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.exception.TransactionException;
import com.example.tests.server.pool.TransactionProvider;

import java.sql.Connection;
import java.util.List;

public class QuestionService {
    private final TransactionProvider transactionProvider = TransactionProvider.getInstance();

    public int add(Question question) {
        int generatedId = 0;

        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            QuestionDao questionDao = new QuestionDao(connection);
            generatedId = questionDao.add(question);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
            e.printStackTrace();
        } finally {
            transactionProvider.endTransaction();
        }
        return generatedId;
    }

    public List<Question> findByTestId(int testId) {
        List<Question> questions = null;
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            QuestionDao questionDao = new QuestionDao(connection);
            questions = questionDao.findAllByTestId(testId);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
            e.printStackTrace();
        } finally {
            transactionProvider.endTransaction();
        }
        return questions;
    }

}
