package com.example.tests.client.controller;

import com.example.tests.client.service.ClientService;
import com.example.tests.client.session.TestsSession;
import com.example.tests.client.session.UserSession;
import com.example.tests.server.entity.Test;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class HomeController implements Controller {
    private final SceneRepository sceneRepository = SceneRepository.getInstance();

    @FXML
    private FlowPane main;


    @FXML
    void initialize() {
        loadTests();
    }

    public void loadTests() {
        try {
            ClientService dataModel = new ClientService();
            List<Test> tests = dataModel.getTests();
            showTests(tests);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            showMessage("Can't get server connection.");
        }
    }

    private void showTests(List<Test> tests) {
        TestsSession.getInstance().setTests(tests);
        main.getChildren().clear();
        for (Test test : tests) {
            VBox testCard = createTestCard(test);
            main.getChildren().add(testCard);
        }
    }

    void runTest(int id) {
        sceneRepository.activate("test");
        TestController testController = (TestController) sceneRepository.getController("test");
        testController.loadTest(id);
    }

    private VBox createTestCard(Test test) {
        VBox testCard = createCardBody(test.getId());
        BorderPane cardHeader = new BorderPane();
        Label testNameLabel = createTestNameLabel(test.getName());
        cardHeader.setLeft(testNameLabel);
        if (UserSession.getInstance().getUser().isAdmin()) {
            Button deleteButton = createDeleteButton(testCard);
            cardHeader.setRight(deleteButton);
        }
        Button runButton = createRunButton(test);
        VBox actionBox = new VBox(runButton);
        actionBox.setMaxWidth(Double.MAX_VALUE);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        testCard.getChildren().addAll(cardHeader, actionBox);
        return testCard;
    }

    private VBox createCardBody(int testId) {
        VBox testCard = new VBox();
        testCard.getStyleClass().add("test-card");
        testCard.setPrefWidth(500);
        testCard.setMaxWidth(Region.USE_PREF_SIZE);
        testCard.setSpacing(50);
        testCard.setAlignment(Pos.TOP_CENTER);
        testCard.setPadding(new Insets(10));
        testCard.setId(String.valueOf(testId));
        return testCard;
    }

    private Button createRunButton(Test test) {
        Button button = new Button("Run test");
        button.getStyleClass().add("run-button");
        button.setOnAction(event -> {
            runTest(test.getId());
        });
        return button;
    }

    private Label createTestNameLabel(String testName) {
        Label testNameLabel = new Label(testName);
        testNameLabel.setMaxWidth(Double.MAX_VALUE);
        testNameLabel.setAlignment(Pos.BASELINE_LEFT);
        testNameLabel.getStyleClass().add("test-name-card");
        return testNameLabel;
    }

    private Button createDeleteButton(VBox testCard) {
        Button deleteButton = new Button("X");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(event -> {
            deleteTestCard(testCard);
        });
        return deleteButton;
    }

    private void deleteTestCard(VBox testCard) {
        try {
            boolean confirmed = showConfirmation();
            if (confirmed) {
                ClientService dataModel = new ClientService();
                dataModel.deleteTest(Integer.parseInt(testCard.getId()));
                main.getChildren().remove(testCard);
            }
        } catch (IOException | ClassNotFoundException e) {
            showErrorMessage("Server connection lost.");
        }
    }

    private boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete image");
        alert.setHeaderText("Are you sure to delete this test?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                return true;
            } else if (option.get() == ButtonType.CANCEL) {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void search(String query) {
        if (query != null && !query.isBlank()) {
            ClientService dataModel = new ClientService();
            try {
                List<Test> tests = dataModel.search(query);
                if (!tests.isEmpty()) {
                    showTests(tests);
                } else {
                    showMessage("Nothing was found.");
                }
            } catch (IOException | ClassNotFoundException e) {
                showMessage("Can't get server connection.");
            }
        } else {
            showMessage("Incorrect query.");
        }
    }

    public void showMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setPrefWidth(500);
        messageLabel.getStyleClass().add("message-label");
        messageLabel.setAlignment(Pos.BASELINE_CENTER);
        messageLabel.setMaxWidth(Region.USE_PREF_SIZE);
        main.getChildren().clear();
        main.getChildren().add(messageLabel);
    }
}
