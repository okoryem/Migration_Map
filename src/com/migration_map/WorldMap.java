package com.migration_map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final Pane root;
    private final Stage primaryStage;
    private Map<String, String> mapPaths;
    private Group mapGroup;
    private final Map <String, SVGPath> svgCountries = new HashMap<>();
    private final Map<SVGPath, String> svgToCode = new HashMap<>();
    private MigrationAPI api;
    private final CountryCodes codes;
    private SVGPath[] compareArray = {null, null};
    private final Label name = new Label();
    private final Button clearButton = new Button();
    private final Button switchButton = new Button();
    //private final ComboBox<String> dropDown = new ComboBox<>();
    private final ComboBox<String> dataSelect = new ComboBox<>();


    WorldMap(Stage primaryStage, Pane root, CountryCodes codes) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.codes = codes;
        this.loadMap();
        this.loadOverLays();
        this.loadScene();
        api = new MigrationAPI(codes);
    }

    public MigrationAPI getApi() {
        return api;
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
        country.setFill(Color.web("#f5a623"));
    }

    public void getRefugees(SVGPath coo) {
        if (coo == compareArray[0] && compareArray[1] == null) {
            return;
        }



        if (compareArray[0] == null && compareArray[1] == null) {
            compareArray[0] = coo;
            coo.setFill(Color.web("#f5a623"));
            coo.setOnMouseEntered(event -> coo.setFill(Color.web("#666666")));
            coo.setOnMouseExited(event -> coo.setFill(Color.web("#f5a623")));


            //System.out.println(api.getIDPs(svgToCode.get(compareArray[0])));

            name.setText("There are "
                    + api.getIDPs(svgToCode.get(compareArray[0])).toString()
                    + " IDPs in "
                    + codes.getCountryName(svgToCode.get(compareArray[0])));

        } else if (compareArray[0] != null && compareArray[1] == null) {
            compareArray[1] = coo;
            coo.setFill(Color.web("#1c9ba0"));
            coo.setOnMouseEntered(event -> coo.setFill(Color.web("#666666")));
            coo.setOnMouseExited(event -> coo.setFill(Color.web("#1c9ba0")));


            name.setText("There are "
                    + api.getRefugees(svgToCode.get(compareArray[0]), svgToCode.get(compareArray[1]), dataSelect.getValue()).toString()
                    + " "
                    + dataSelect.getValue()
                    + " from "
                    + codes.getCountryName(svgToCode.get(compareArray[0]))
                    + " in "
                    + codes.getCountryName(svgToCode.get(compareArray[1])));

        } else if (compareArray[0] != null && compareArray[1] != null) {

            SVGPath first = compareArray[0];
            SVGPath second = compareArray[1];

            first.setFill(Color.web("#3b3b3b"));
            first.setOnMouseEntered(event -> first.setFill(Color.web("#666666")));
            first.setOnMouseExited(event -> first.setFill(Color.web("#3b3b3b")));

            second.setFill(Color.web("#3b3b3b"));
            second.setOnMouseEntered(event -> second.setFill(Color.web("#666666")));
            second.setOnMouseExited(event -> second.setFill(Color.web("#3b3b3b")));

            compareArray[0] = null;
            compareArray[1] = null;

            getRefugees(coo);
        }
    }

    private void switchCountries() {
        if (compareArray[1] == null) {
            return;
        }

        SVGPath first = compareArray[0];
        SVGPath second = compareArray[1];

        first.setFill(Color.web("#3b3b3b"));
        first.setOnMouseEntered(event -> first.setFill(Color.web("#666666")));
        first.setOnMouseExited(event -> first.setFill(Color.web("#3b3b3b")));

        second.setFill(Color.web("#3b3b3b"));
        second.setOnMouseEntered(event -> second.setFill(Color.web("#666666")));
        second.setOnMouseExited(event -> second.setFill(Color.web("#3b3b3b")));

        SVGPath tempFirst = compareArray[0];
        SVGPath tempSecond = compareArray[1];

        getRefugees(tempSecond);
        getRefugees(tempFirst);
    }

    private void clearArray() {
        SVGPath first = compareArray[0];
        SVGPath second = compareArray[1];


        if (compareArray[0] != null) {
            first.setFill(Color.web("#3b3b3b"));
            first.setOnMouseEntered(event -> first.setFill(Color.web("#666666")));
            first.setOnMouseExited(event -> first.setFill(Color.web("#3b3b3b")));
        }

        if (compareArray[1] != null) {
            second.setFill(Color.web("#3b3b3b"));
            second.setOnMouseEntered(event -> second.setFill(Color.web("#666666")));
            second.setOnMouseExited(event -> second.setFill(Color.web("#3b3b3b")));
        }

        compareArray[0] = null;
        compareArray[1] = null;

        name.setText("");
    }


    private void loadMap () {
        try {
            mapPaths = SVGPathExtractor.extractPaths("/Users/macbook/IdeaProjects/Migration_Map/world.svg");
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
                mapPath.setOnMouseClicked(event -> getRefugees(mapPath));

                mapGroup.getChildren().add(mapPath);

                svgCountries.put(id, mapPath);
                svgToCode.put(mapPath, id);
            }
            mapGroup.setLayoutX(200);
            mapGroup.setLayoutY(50);
            mapGroup.setScaleX(1.1);
            mapGroup.setScaleY(1.1);
            root.getChildren().add(mapGroup);
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
        root.setStyle("-fx-background-color: #1c1c1c;");
        Pane labels = new Pane();

        // Information Display
        name.setText("");
        name.setLayoutX(30);
        name.setLayoutY(675);

        name.setPrefWidth(350);
        name.setPrefHeight(75);
        name.setWrapText(true);

        name.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
        name.setEffect(dropShadow);
        //labels.getChildren().add(name);
        this.root.getChildren().add(name);


        // Clear Button
        clearButton.setText("CLEAR");
        clearButton.setLayoutX(1300);
        clearButton.setLayoutY(30);

        clearButton.setPrefWidth(100);
        clearButton.setPrefHeight(15);

        clearButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );

        clearButton.setOnMouseEntered(event -> clearButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #666666;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        ));
        clearButton.setOnMouseExited(event -> clearButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        ));
        clearButton.setOnMouseClicked(event -> clearArray());

        clearButton.setEffect(dropShadow);

        this.root.getChildren().add(clearButton);


        // Switch button
        switchButton.setText("SWITCH");

        switchButton.setLayoutX(30);
        switchButton.setLayoutY(600);

        switchButton.setPrefWidth(100);
        switchButton.setPrefHeight(15);

        switchButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );

        switchButton.setOnMouseEntered(event -> switchButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #666666;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        ));
        switchButton.setOnMouseExited(event -> switchButton.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        ));
        switchButton.setOnMouseClicked(event -> switchCountries());
        switchButton.setEffect(dropShadow);
        this.root.getChildren().add(switchButton);

        /*
        #################
        #################
        #################

        Add drop down menu for country selection

        #################
        #################
        #################
         */

        /*
        // Dropdown menu
        for (String each : codes.getCountriesNames()) {
            dropDown.getItems().add(codes.getCountryName(each));
        }

        dropDown.setOnAction(event -> {
            String selectedOption = dropDown.getValue();
            getRefugees(svgCountries.get(codes.getNamesToCode().get(selectedOption)));
        });

        dropDown.setLayoutX(30);
        dropDown.setLayoutY(30);

        dropDown.setPrefWidth(100);
        dropDown.setPrefHeight(15);

        this.root.getChildren().add(dropDown);
         */

        // Refugees vs Asylum Seekers dropdown menu
        dataSelect.getItems().addAll("refugees", "asylum seekers");
        dataSelect.setValue("refugees");

        dataSelect.setLayoutX(150);
        dataSelect.setLayoutY(610);

        dataSelect.setPrefWidth(100);
        dataSelect.setPrefHeight(15);

        this.root.getChildren().add(dataSelect);


    }
}
