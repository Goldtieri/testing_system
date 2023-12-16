package com.example.tests.client.controller;

import com.example.tests.client.session.UserSession;
import com.example.tests.server.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController implements Controller {
    private final SceneRepository sceneRepository = SceneRepository.getInstance();

    @FXML
    private TextField searchInput;

    @FXML
    private BorderPane main;

    @FXML
    private Button createButton;

    @FXML
    public void initialize() throws IOException {
        User user = UserSession.getInstance().getUser();
        if (!user.isAdmin()) {
            createButton.setVisible(false);
        }

        sceneRepository.setMain(main);
        sceneRepository.loadPane("/com/example/tests/createTest.fxml", "createTest");
        sceneRepository.loadPane("/com/example/tests/home.fxml", "home");
        sceneRepository.loadPane("/com/example/tests/test.fxml", "test");
        sceneRepository.loadPane("/com/example/tests/result.fxml", "result");
        sceneRepository.activate("home");
    }

    @FXML
    void openCreateTestPage(ActionEvent event) {
        sceneRepository.activate("createTest");
        TestCreatorController testCreatorController = (TestCreatorController) sceneRepository.getController("createTest");
        testCreatorController.clear();

    }

    @FXML
    void openHomePage(ActionEvent event) {
        sceneRepository.activate("home");
        HomeController homeController = (HomeController) sceneRepository.getController("home");
        homeController.loadTests();

    }

    @FXML
    void search(ActionEvent event) {
        sceneRepository.activate("home");
        HomeController homeController = (HomeController) sceneRepository.getController("home");
        homeController.search(searchInput.getText());
    }

    @FXML
    public void onEnter(ActionEvent event) throws IOException {
        search(event);
    }

}
