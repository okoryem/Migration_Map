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
    private final Pane root;
    private final Stage primaryStage;
    private Map<String, String> mapPaths;
    private Group mapGroup;
    private final Map <String, SVGPath> svgCountries = new HashMap<>();
    private final Map<SVGPath, String> svgToCode = new HashMap<>();
    private MigrationAPI api;
    private final CountryCodes codes;
    private SVGPath[] compareArray = {null, null};

    WorldMap(Stage primaryStage, Pane root, CountryCodes codes) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.codes = codes;
        this.loadMap();
        //this.loadOverLays();
        this.loadScene();
        api = new MigrationAPI(codes);
        //System.out.println(api.getRefugees("2022", "CO", "US"));
        //System.out.println(svgCountries.get("CO"));
        //System.out.println(svgToCode.get(getCountry("CO")));
        //System.out.println("conversion: " + svgToCode.get("CO"));
        //System.out.println("conversion: " + svgToCode.get("AF"));
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
        country.setFill(Color.YELLOW);
    }

    public void getRefugees(SVGPath coo, String year) {
        if (coo == compareArray[0] && compareArray[1] == null) {
            return;
        }



        if (compareArray[0] == null && compareArray[1] == null) {
            compareArray[0] = coo;
            coo.setFill(Color.YELLOW);
            coo.setOnMouseExited(event -> coo.setFill(Color.YELLOW));
            System.out.println(api.getIDPs(svgToCode.get(compareArray[0])));
        } else if (compareArray[0] != null && compareArray[1] == null) {
            compareArray[1] = coo;
            coo.setFill(Color.BLUE);
            coo.setOnMouseExited(event -> coo.setFill(Color.BLUE));
            System.out.println(api.getRefugees(svgToCode.get(compareArray[0]), svgToCode.get(compareArray[1])));
        } else if (compareArray[0] != null && compareArray[1] != null) {
            compareArray[0].setFill(Color.web("#3b3b3b"));
            compareArray[0].setOnMouseExited(event -> compareArray[0].setFill(Color.web("#3b3b3b")));

            compareArray[1].setFill(Color.web("#3b3b3b"));
            compareArray[1].setOnMouseExited(event -> compareArray[1].setFill(Color.web("#3b3b3b")));

            if (coo == compareArray[1]) {
                switchCountries();
            } else {
                compareArray[0] = null;
                compareArray[1] = null;
                getRefugees(coo, year);
            }

        }
    }

    private void switchCountries() {
        SVGPath temp = compareArray[0];
        compareArray[0] = compareArray[1];
        compareArray[1] = temp;

        compareArray[0].setFill(Color.YELLOW);
        compareArray[0].setOnMouseExited(event -> compareArray[0].setFill(Color.YELLOW));

        compareArray[1].setFill(Color.BLUE);
        compareArray[1].setOnMouseExited(event -> compareArray[1].setFill(Color.BLUE));
        System.out.println(api.getRefugees(svgToCode.get(compareArray[0]), svgToCode.get(compareArray[1])));
    }
    /*
    getRefugees from two countries draft

    have a class attribute array
        set it to empty
        when a country is clicked check is array[0] is null/empty
            if null/empty add it country to array[0] and do nothing for now
                (need to figure how to get the top 5 countries to and from)
            if array[0] isn't null/empty add it to array[1] and run method above
                with the parameters of array[0] and array[1]

        If you want to switch have a function thats switches array[0] and array[1]
        then run the same function. And make sure to switch the colors.

     */


    private void sayHi() {
        //MigrationAPI api = new MigrationAPI(codes);
        //System.out.println("HI");
        //System.out.println(api.getRefugees(svgToCode.get("CO"), "US"));
        //System.out.println(api.getRefugees(svgToCode.get("AF"), "US"));
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
                //mapPath.setOnMouseClicked(event -> System.out.println(getRefugees(mapPath, "2022")));
                mapPath.setOnMouseClicked(event -> getRefugees(mapPath, "2022"));
                //mapPath.setOnMouseClicked(event -> sayHi());

                mapGroup.getChildren().add(mapPath);

                svgCountries.put(id, mapPath);
                svgToCode.put(mapPath, id);
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
