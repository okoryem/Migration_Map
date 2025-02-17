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
import javafx.scene.text.Text;
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

    private Text paragraph = new Text();

    /*
     * Constructor for the WorldMap Class
     *
     *
     *
     *
     */
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

    /*
     * This method fetches and displays the IDPs, Refugees, or Asylum Seekers
     * based on a given SVGPath. The method also sets the colors of the SVGPaths
     *
     * @param coo (country of origin) is an SVGPath
     * @return void
     */
    public void getRefugees(SVGPath coo) {
        // Checks to see if country is already selected (do nothing)
        if (coo == compareArray[0] && compareArray[1] == null) {
            changeText();
            return;
        }


        if (compareArray[0] == null && compareArray[1] == null) {
            /*
             If no country is previous selected paint
             chosen SVGPath (country) yellow
             and display the number of Internally
             Displaced People in said country
             */
            compareArray[0] = coo;
            coo.setFill(Color.web("#f5a623"));
            coo.setOnMouseEntered(event -> coo.setFill(Color.web("#666666")));
            coo.setOnMouseExited(event -> coo.setFill(Color.web("#f5a623")));

            name.setText("There are "
                    + api.getIDPs(svgToCode.get(compareArray[0])).toString()
                    + " Internally Displaced Persons in "
                    + codes.getCountryName(svgToCode.get(compareArray[0])));
        } else if (compareArray[0] != null && compareArray[1] == null) {
            /*
             If country is previous selected paint
             chosen SVGPath (country) blue and
             display refugees or asylum seekers
             from country of origin to country of
             asylum depending on dropdown box selection
             */
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
            /*
             If two countries are already selected
             Reset the color of both SVGPaths and
             recursively call this function
             */
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
            changeText();
            getRefugees(coo);
        }
        changeText();
    }

    /*
     * This method switches the country of origin to the country of
     * asylum and the country of asylum to the country of origin
     *
     * @param void
     * @return void
     */
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

    /*
     * This method clears the array that is storing the
     * SVGPaths and sets them to the original color
     *
     * @param void
     * @return void
     */
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
        changeText();
    }

    /*
     * This method loads the SVG in the window. The SVGPaths are also loaded
     * where the properties are set and are put into HashMaps for accessing.
     *
     * @param void
     * @return void
     */
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

    /*
     * This method loads the JavaFX scene.
     *
     * @param void
     * @return void
     */
    private void loadScene() {
        Scene scene = new Scene(root, 800, 600, Color.web("1c1c1c"));
        primaryStage.setTitle("Migration Map");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /*
     * This method loads and sets the properties of the JavaFX
     * overlays (Data display label, Clear Button, Switch Button,
     * and data Selection dropdown menu.
     *
     * @param void
     * @return void
     */
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
                "-fx-font-size: 18px;" +
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
        
        dataSelect.setOnAction(event -> changeText());

        dataSelect.setLayoutX(150);
        dataSelect.setLayoutY(610);

        dataSelect.setPrefWidth(100);
        dataSelect.setPrefHeight(15);

        this.root.getChildren().add(dataSelect);
        
        paragraph.setLayoutX(50);
        paragraph.setLayoutY(40);

        paragraph.setWrappingWidth(250);
        paragraph.setStyle("-fx-text-fill: #f0f0f0;" +
                "-fx-background-color: #3b3b3b;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Arial', 'Verdana';" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );
        paragraph.setFill(Color.web("#f0f0f0"));

        this.root.getChildren().add(paragraph);
    }
    
    private void changeText() {
        if (compareArray[0] == null) {
            paragraph.setText("");
            return;
        }

        if (compareArray[1] == null) {
            paragraph.setText("Internally Displace Person: " +
                    "Persons or groups of persons who have been forced or " +
                    "obliged to flee or to leave their homes or places of habitual residence, " +
                    "in particular as a result of or in order to avoid the effects of armed " +
                    "conflict, situations of generalized violence, violations of human rights " +
                    "or natural or human-made disasters, and who have not crossed an internationally " +
                    "recognized state border." +
                    "\n" +
                    "- UNHCR");
            return;
        }

        switch (dataSelect.getValue()) {
            case "refugees":
                paragraph.setText("The 1951 Refugee Convention defines a refugee as a person who" +
                        "owing to well-founded fear of being persecuted for reasons of race, religion, " +
                        "nationality, membership of a particular social group or political opinion, " +
                        "is outside the country of [their] nationality and is unable or, owing to such " +
                        "fear, is unwilling to avail [themself] of the protection of that country." +
                        "\n" +
                        "- UNHCR");
                break;
            case "asylum seekers":
                paragraph.setText("An asylum-seeker is someone who intends to seek or is awaiting a " +
                        "decision on their request for international protection. In some countries, " +
                        "it is used as a legal term for a person who has applied for refugee status " +
                        "and has not yet received a final decision on their claim." +
                        "\n" +
                        "- UNHCR");
                break;
            default:
                paragraph.setText("");
                break;
        }

    }
}
