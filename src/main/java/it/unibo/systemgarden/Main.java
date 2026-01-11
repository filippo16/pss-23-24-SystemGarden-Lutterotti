package it.unibo.systemgarden;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for SystemGarden
 */
public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) {
        
        System.out.println("[SystemGarden] Application started");
        primaryStage.setTitle("SystemGarden - Gestione Aree Verdi");
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
