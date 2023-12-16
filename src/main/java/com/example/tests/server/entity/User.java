package com.example.tests.server.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = 2727837112994635019L;

    private int id;
    private String login;
    private boolean isAdmin;

    public User(int id, String login, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, isAdmin);
    }
}
