package com.example.tests.client.controller;

import com.example.tests.client.service.ClientService;
import com.example.tests.client.exception.InputException;
import com.example.tests.server.entity.Option;
import com.example.tests.server.entity.Question;
import com.example.tests.server.entity.Test;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestCreatorController implements Controller {

    @FXML
    private VBox options;

    @FXML
    private VBox questions;

    @FXML
    private TextField testNameField;

    public void clear() {
        testNameField.clear();
        questions.getChildren().clear();
        addQuestion();
    }

    @FXML
    void addOption(ActionEvent event) {
        addOptionForm(options);
    }

    @FXML
    void addQuestion() {
        questions.getChildren().add(createQuestionForm());
    }

    @FXML
    private void createTest(ActionEvent event) {
        ObservableList<Node> questionList = questions.getChildren();
        try {
            List<Question> questions = new ArrayList<>();

            for (Node node : questionList) {
                VBox questionBox = (VBox) node;
                VBox questionForm = (VBox) questionBox.getChildren().get(0);

                TextField questionField = (TextField) questionForm.getChildren().get(0);
                String questionContent = questionField.getText();
                if (questionContent.isEmpty()) {
                    throw new InputException("Question content is empty.");
                }
                VBox optionsBox = (VBox) questionForm.getChildren().get(1);
                List<Option> options = getOptions(optionsBox);

                Question question = new Question(questionContent, options);
                questions.add(question);
            }
            String testName = testNameField.getText();
            if (testName.isEmpty()) {
                throw new InputException("Text name is empty.");
            }
            Test test = new Test(testName, questions);
            ClientService clientService = new ClientService();
            clientService.uploadTest(test);
        } catch (IOException | ClassNotFoundException e) {
            showMessage("Can't get server connection.");
        } catch (InputException e) {
            showMessage(e.getMessage());
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private List<Option> getOptions(VBox optionsBox) throws InputException {
        List<Option> options = new ArrayList<>();

        for (Node node : optionsBox.getChildren()) {
            HBox optionBox = (HBox) node;
            TextField optionField = (TextField) optionBox.getChildren().get(0);
            RadioButton radioButton = (RadioButton) optionBox.getChildren().get(1);
            String optionContent = optionField.getText();

            if (optionContent.isEmpty()) {
                throw new InputException("Option content is empty.");
            }
            boolean isSelected = radioButton.isSelected();
            Option option = new Option(optionContent, isSelected);
            options.add(option);
        }
        return options;
    }

    private void addOptionForm(VBox optionsBox) {
        ToggleGroup toggleGroup;
        if (optionsBox.getChildren().isEmpty()) {
            toggleGroup = new ToggleGroup();
        } else {
            toggleGroup = getOptionsToggleGroup(optionsBox);
        }

        TextField optionField = createOptionField("Option " + (optionsBox.getChildren().size() + 1));
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(toggleGroup);
        if (toggleGroup.getToggles().size() == 1) {
            radioButton.setSelected(true);
        }

        HBox optionBox = new HBox(optionField, radioButton);
        optionBox.setAlignment(Pos.CENTER_LEFT);
        optionBox.setSpacing(10);
        optionsBox.getChildren().add(optionBox);
    }

    private TextField createOptionField(String prompt) {
        TextField optionField = new TextField();
        optionField.setFont(new Font(16));
        optionField.setPrefWidth(280);
        optionField.setPromptText(prompt);
        return optionField;
    }

    private ToggleGroup getOptionsToggleGroup(VBox optionsBox) {
        HBox optionBox = (HBox) optionsBox.getChildren().get(0);
        RadioButton radioButton = (RadioButton) optionBox.getChildren().get(1);
        return radioButton.getToggleGroup();
    }

    private VBox createQuestionForm() {
        String questionId = generateQuestionId();

        TextField questionField = new TextField();
        questionField.setPromptText("Question " + questionId);
        questionField.setFont(new Font(16));
        questionField.setPrefWidth(400);

        VBox options = new VBox();
        options.setPadding(new Insets(0, 0, 0, 20));
        options.setSpacing(20);
        addOptionForm(options);
        addOptionForm(options);

        Button button = new Button("+");
        button.setFont(new Font(14));
        button.setOnAction(event -> {
            addOptionForm(options);
            if (options.getChildren().size() > 4) {
                button.setVisible(false);
            }
        });

        VBox buttonBox = new VBox(button);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(0, 0, 0, 20));

        VBox questionForm = new VBox(questionField, options, buttonBox);
        questionForm.setId(questionId);
        questionForm.setSpacing(20);
        questionForm.setMaxWidth(Region.USE_PREF_SIZE);

        VBox vBox = new VBox(questionForm);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(500);
        vBox.setMaxWidth(Region.USE_PREF_SIZE);
        vBox.setPadding(new Insets(20));
        vBox.getStyleClass().add("question-form");
        return vBox;
    }

    private String generateQuestionId() {
        return String.valueOf(questions.getChildren().size() + 1);
    }
}
