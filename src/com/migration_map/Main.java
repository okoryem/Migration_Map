package com.migration_map;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.w3c.dom.Element;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) {
        CountryCodes codes = new CountryCodes();
        Pane root = new Pane();
        WorldMap worldMap = new WorldMap(primaryStage, root, codes);
        //System.out.println(worldMap.getCountry("AG"));
        worldMap.paintCountry("CO");
        //MigrationAPI api = new MigrationAPI(codes);

        System.out.println(worldMap.getApi().getRefugees("CO", "US"));
        System.out.println(worldMap.getApi().getRefugees("UA", "US"));
        //System.out.println(codes.convertCode("CO"));
    }

    public static void main(String[] args) {
        launch(args);
    }

}