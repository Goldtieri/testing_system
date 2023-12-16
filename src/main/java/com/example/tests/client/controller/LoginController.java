package com.example.tests.client.controller;

import com.example.tests.client.service.ClientService;
import com.example.tests.client.session.UserSession;
import com.example.tests.server.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements Controller {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    void login(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        try {
            if (login.length() > 2 && password.length() > 2) {
                ClientService dataModel = new ClientService();
                User user = dataModel.login(login, password);
                if (user != null) {
                    UserSession.getInstance().setUser(user);
                    goToMainPage(event);
                } else {
                    messageLabel.setText("Incorrect login or password.");
                }
            } else {
                messageLabel.setText("Incorrect data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            messageLabel.setText("Can't connect to server.");
        }
    }

    private void goToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tests/main.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
}
