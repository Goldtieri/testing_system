package com.example.tests.client.controller;

import com.example.tests.server.entity.TestResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultController implements Controller {
    @FXML
    private Label result;

    public void showResult(TestResult testResult) {
        result.setText(String.format("%d / %d", testResult.getCorrectAnswers(), testResult.getTotalAnswers()));
    }

    @FXML
    void goToHomePage(ActionEvent event) {
        SceneRepository.getInstance().activate("home");
    }


}
