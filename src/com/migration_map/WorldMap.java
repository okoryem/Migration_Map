package com.migration_map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private Pane root;
    private Stage primaryStage;
    private Map<String, String> mapPaths;
    private Group mapGroup;
    private Map <String, SVGPath> svgCountries = new HashMap<>();

    WorldMap(Stage primaryStage, Pane root) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.loadMap();
        //this.loadOverLays();
        this.loadScene();
        MigrationAPI api = new MigrationAPI();
        System.out.println(api.getRefugees("2022", "CO", "US"));
    }

    public Pane getRoot() {
        return root;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Map<String, String> getMapPaths() {
        return mapPaths;
    }

    public SVGPath getCountry(String name) {
        return svgCountries.get(name);
    }

    public void paintCountry(String name) {
        SVGPath country = getCountry(name);
        country.setFill(Color.YELLOW);
    }

    private void loadMap () {
        try {
            mapPaths = SVGPathExtractor.extractPaths("/Users/macbook/IdeaProjects/Migration_Map/world.svg");
            //System.out.println(getCountry("AF"));
            mapGroup = new Group();

            for (Map.Entry<String, String> entry : mapPaths.entrySet()) {
                String id = entry.getKey();
                String pathData = entry.getValue();

                SVGPath mapPath = new SVGPath();
                mapPath.setContent(pathData);
                mapPath.setFill(Color.web("#3b3b3b"));
                mapPath.setStroke(Color.web("#4b4b4b"));

                mapPath.setOnMouseEntered(event -> mapPath.setFill(Color.web("#666666")));
                mapPath.setOnMouseExited(event -> mapPath.setFill(Color.web("#3b3b3b")));

                mapGroup.getChildren().add(mapPath);

                svgCountries.put(id, mapPath);
            }
            mapGroup.setLayoutX(200);
            mapGroup.setLayoutY(50);
            mapGroup.setScaleX(1.1);
            mapGroup.setScaleY(1.1);
            root.getChildren().add(mapGroup);
            //loadOverLays();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadScene() {
        Scene scene = new Scene(root, 800, 600, Color.web("1c1c1c"));
        primaryStage.setTitle("Migration Map");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void loadOverLays() {
        Pane labels = new Pane();
        Label name = new Label();
        name.setText("Hi");
        name.setLayoutX(30);
        name.setLayoutY(30);
        labels.getChildren().add(name);
        this.root.getChildren().add(labels);
    }
}
