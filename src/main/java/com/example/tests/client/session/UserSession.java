package com.example.tests.client.session;

import com.example.tests.server.entity.User;

public class UserSession {
    private static UserSession instance;
    private User user;

    private UserSession() {

    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
