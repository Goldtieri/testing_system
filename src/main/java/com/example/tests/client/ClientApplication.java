package com.example.tests.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    public static final String CONNECTION_WINDOW_PATH = "/com/example/tests/connectionWindow.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(CONNECTION_WINDOW_PATH));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Automated test system");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
