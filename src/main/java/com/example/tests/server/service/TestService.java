package com.example.tests.server.service;

import com.example.tests.server.dao.TestDao;
import com.example.tests.server.entity.Question;
import com.example.tests.server.entity.Test;
import com.example.tests.server.exception.DaoException;
import com.example.tests.server.exception.TransactionException;
import com.example.tests.server.pool.TransactionProvider;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestService {
    private final TransactionProvider transactionProvider = TransactionProvider.getInstance();

    public int add(Test test) {
        int testId = 0;
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            TestDao testDao = new TestDao(connection);
            testId = testDao.add(test);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
        } finally {
            transactionProvider.endTransaction();
        }
        return testId;
    }

    public List<Test> getAll() {
        List<Test> tests = null;
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            TestDao testDao = new TestDao(connection);
            tests = testDao.getAll();
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
        } finally {
            transactionProvider.endTransaction();
        }
        return tests;
    }


    public Optional<Test> findById(int id) {
        Optional<Test> test = Optional.empty();
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            TestDao testDao = new TestDao(connection);
            test = testDao.findById(id);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
        } finally {
            transactionProvider.endTransaction();
        }
        return test;
    }

    public void upload(Test test) {
        int testId = add(test);
        QuestionService questionService = new QuestionService();
        OptionService optionService = new OptionService();

        for (Question question : test.getQuestions()) {
            question.setTestId(testId);
            int questionId = questionService.add(question);
            optionService.addAll(question.getOptions(), questionId);
        }
    }

    public List<Test> search(String query) {
        List<Test> tests = new ArrayList<>();
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            TestDao testDao = new TestDao(connection);
            tests = testDao.searchByName(query);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
        } finally {
            transactionProvider.endTransaction();
        }
        return tests;
    }

    public void deleteTest(int id) {
        try {
            transactionProvider.initializeTransaction();
            Connection connection = transactionProvider.getConnection();
            TestDao testDao = new TestDao(connection);
            testDao.delete(id);
            transactionProvider.commit();
        } catch (TransactionException | DaoException e) {
            transactionProvider.rollback();
        } finally {
            transactionProvider.endTransaction();
        }
    }
}
