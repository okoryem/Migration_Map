package com.migration_map;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) {
        CountryCodes codes = new CountryCodes();
        Pane root = new Pane();
        WorldMap worldMap = new WorldMap(primaryStage, root, codes);
    }

    public static void main(String[] args) {
        launch(args);
    }

}