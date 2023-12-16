package com.example.tests.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneRepository {
    private static SceneRepository instance;

    private final Map<String, Pane> panes = new HashMap<>();
    private final HashMap<String, Controller> controllers = new HashMap<>();

    private BorderPane main;

    private SceneRepository() {
    }

    public static SceneRepository getInstance() {
        if (instance == null) {
            instance = new SceneRepository();
        }
        return instance;
    }

    public void setMain(BorderPane main) {
        this.main = main;
    }

    public void loadPane(String fxmlFilename, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilename));
        Pane pane = loader.load();
        Controller controller = loader.getController();
        controllers.put(name, controller);
        panes.put(name, pane);
    }

    public void activate(String name) {
        Pane pane = panes.get(name);
        main.setCenter(pane);
    }

    public Controller getController(String name) {
        return controllers.get(name);
    }


}
