package it.unibo.systemgarden.view.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.api.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class ViewImpl implements View {

    private static final String FXML_PATH = "fxml/MainView.fxml";
    private static final String CSS_PATH = "css/style.css";
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;

    private final Stage primaryStage;
    private MainViewHandler mainHandler;
    private Controller controller;

    public ViewImpl(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setController(final Controller controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        try {
            final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML_PATH));
            final Parent root = loader.load();
            final Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            final String css = ClassLoader.getSystemResource(CSS_PATH).toExternalForm();
            
            this.mainHandler = loader.getController();
            this.mainHandler.setCssStylesheet(css);
            this.mainHandler.setController(controller);

            if (css != null) {
                scene.getStylesheets().add(css);
            }

            primaryStage.setTitle("SystemGarden - Gestione Aree Verdi");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error in show view method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addAreaCard( final GreenArea area ) {
        mainHandler.addAreaCard( area );
    }

    @Override
    public void removeAreaCard( final String areaId ) {
        mainHandler.removeAreaCard( areaId );
    }

    @Override
    public void refreshAreaCard( final GreenArea area ) {
        mainHandler.refreshAreaCard( area );
    }

    @Override
    public void updateAreaClock( String areaId, LocalTime time ) {    
        mainHandler.updateAreaClock( areaId, time );                  
    }



    @Override
    public void addSectorCard( final String areaId, final Sector sector ) {
        mainHandler.addSectorCard( areaId, sector );
    }

    @Override
    public void removeSectorCard( final String areaId, final String sectorId ) {
        mainHandler.removeSectorCard( areaId, sectorId );
    }

    @Override
    public void refreshSectorCard( final String areaId, final Sector sector ) {
        mainHandler.refreshSectorCard( areaId, sector );
    }
}