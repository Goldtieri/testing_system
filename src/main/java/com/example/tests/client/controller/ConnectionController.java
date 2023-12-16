package com.example.tests.client.controller;

import com.example.tests.client.TcpClient;
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

public class ConnectionController implements Controller {
    @FXML
    private TextField serverName;

    @FXML
    private Label messageLabel;

    @FXML
    void configure(ActionEvent event) throws IOException {
        String server = serverName.getText();
        TcpClient client = TcpClient.getInstance();
        if (server.equalsIgnoreCase("server")) {
            client.initialize("127.0.0.1", 5555);
            goToLoginPage(event);
        } else {
            messageLabel.setText("Server not found.");
        }
    }

    private void goToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tests/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
