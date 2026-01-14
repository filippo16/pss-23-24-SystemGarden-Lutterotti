package it.unibo.systemgarden.view.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.view.api.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX implementation of View.
 */
public class ViewImpl implements View {

    private static final String FXML_PATH = "/fxml/MainView.fxml";
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
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            final Parent root = loader.load();

            this.mainHandler = loader.getController(); // Get the controller instance from FXMLLoader (fx:controller)

            final Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);


            primaryStage.setTitle("SystemGarden - Gestione Aree Verdi");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAreaDialog() {
        // Implementation for showing area dialog
    }

    @Override
    public void updateGreenAreas(final List<GreenArea> areas) {
      
    }
}
