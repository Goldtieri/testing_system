package com.example.tests.server.service;

import com.example.tests.server.dao.OptionDao;
import com.example.tests.server.entity.Option;
import com.example.tests.server.entity.TestResult;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.exception.TransactionException;
import com.example.tests.server.pool.TransactionProvider;

import java.sql.Connection;
import java.util.List;

public class OptionService {
    private final TransactionProvider transactionProvider = TransactionProvider.getInstance();

    public void addAll(List<Option> options, int questionId) {
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            OptionDao optionDao = new OptionDao(connection);
            for (Option option : options) {
                optionDao.add(option, questionId);
            }
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
            e.printStackTrace();
        } finally {
            transactionProvider.endTransaction();
        }
    }

    public List<Option> findByQuestionId(int questionId) {
        List<Option> options = null;
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            OptionDao optionDao = new OptionDao(connection);
            options = optionDao.findAllByQuestionId(questionId);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
            e.printStackTrace();
        } finally {
            transactionProvider.endTransaction();
        }
        return options;
    }


    public TestResult getResult(List<String> answers) {
        int correctAnswers = 0;
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            OptionDao optionDao = new OptionDao(connection);
            for (String answerId : answers) {
                boolean isCorrect = optionDao.isCorrect(Integer.parseInt(answerId));
                if (isCorrect) {
                    correctAnswers++;
                }
            }
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
            e.printStackTrace();
        } finally {
            transactionProvider.endTransaction();
        }
        return new TestResult(correctAnswers, answers.size());
    }


}
