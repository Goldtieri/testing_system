package com.example.tests.client.controller;

import com.example.tests.client.service.ClientService;
import com.example.tests.server.entity.Option;
import com.example.tests.server.entity.Question;
import com.example.tests.server.entity.Test;
import com.example.tests.server.entity.TestResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.*;


public class TestController implements Controller {
    private int numerator;

    @FXML
    private Label testName;

    @FXML
    private VBox questionForms;


    @FXML
    void initialize() {
    }

    public void loadTest(int testId) {
        ClientService dataModel = new ClientService();
        try {
            Test test = dataModel.getTestById(testId);
            if (test != null) {
                testName.setText("Test: " + test.getName());
                questionForms.getChildren().clear();
                List<Question> questions = dataModel.getQuestions(test.getId());
                numerator = 1;
                for (Question question : questions) {
                    addQuestion(question);
                }
            } else {
                showErrorMessage("Error: Test wasn't found");
            }
        } catch (IOException | ClassNotFoundException e) {
            showErrorMessage("Can't get server connection.");
        }
    }

    private void showErrorMessage(String message) {
        Label label = new Label(message);
        label.setPrefWidth(700);
        label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setFont(new Font(16));
        questionForms.getChildren().add(label);
    }

    private List<Option> loadOptions(int questionId) {
        ClientService dataModel = new ClientService();
        List<Option> options = new ArrayList<>();
        try {
            options = dataModel.getOptions(questionId);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return options;
    }

    private void addQuestion(Question question) {
        VBox questionForm = createQuestionForm(question);
        questionForms.getChildren().add(questionForm);
    }

    private VBox createQuestionForm(Question question) {
        Label questionContent = createQuestionContent(question);
        VBox optionsBox = createOptionsBox(question);
        VBox questionForm = new VBox(questionContent, optionsBox);
        questionForm.setId(String.valueOf(question.getId()));
        styleQuestionForm(questionForm);
        return questionForm;
    }

    private void styleQuestionForm(VBox questionForm) {
        questionForm.getStyleClass().add("question-form");
        questionForm.setPrefWidth(700);
        questionForm.setMaxWidth(Region.USE_PREF_SIZE);
        questionForm.setPadding(new Insets(20));
    }

    private Label createQuestionContent(Question question) {
        int number = numerator++;
        Label questionContent = new Label(String.format("%d. %s", number, question.getContent()));
        questionContent.setFont(new Font(14));
        questionContent.setMaxWidth(Double.MAX_VALUE);
        questionContent.setAlignment(Pos.BASELINE_LEFT);
        return questionContent;
    }

    private VBox createOptionsBox(Question question) {
        List<Option> options = loadOptions(question.getId());
        ToggleGroup toggleGroup = new ToggleGroup();
        VBox optionsBox = new VBox();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            RadioButton optionButton = new RadioButton(option.getContent());
            optionButton.setToggleGroup(toggleGroup);
            optionButton.setId(String.valueOf(option.getId()));
            if (i == 0) {
                optionButton.setSelected(true);
            }
            optionsBox.getChildren().add(optionButton);
        }
        optionsBox.setPadding(new Insets(20, 0, 0, 20));
        optionsBox.setSpacing(20);
        return optionsBox;
    }

    @FXML
    public void submitTest(ActionEvent event) {
        List<String> answers = getAnswers();
        ClientService dataModel = new ClientService();
        try {
            TestResult testResult = dataModel.uploadAnswers(answers);
            showResultPage(testResult);
        } catch (IOException | ClassNotFoundException e) {
            showErrorMessage("Can't get server connection.");
        }

    }

    public void showResultPage(TestResult testResult) {
        SceneRepository sceneRepository = SceneRepository.getInstance();
        sceneRepository.activate("result");
        ResultController resultController = (ResultController) sceneRepository.getController("result");
        resultController.showResult(testResult);
    }


    private List<String> getAnswers() {
        List<String> answers = new ArrayList<>();

        for (Node node : questionForms.getChildren()) {
            VBox questionForm = (VBox) node;
            VBox options = (VBox) questionForm.getChildren().get(1);
            RadioButton option = (RadioButton) options.getChildren().get(0);
            ToggleGroup toggleGroup = option.getToggleGroup();
            RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
            String optionId = selectedOption.getId();
            answers.add(optionId);
        }
        return answers;
    }

}
