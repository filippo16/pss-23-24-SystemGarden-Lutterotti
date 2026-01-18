package it.unibo.systemgarden;

import it.unibo.systemgarden.controller.impl.ControllerImpl;
import it.unibo.systemgarden.view.api.View;
import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.view.impl.ViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for SystemGarden
 */
public class Main extends Application {
    private Controller controller;

    @Override
    public void start( final Stage primaryStage ) {
        
        final View view = new ViewImpl( primaryStage );

        controller = new ControllerImpl( view );

        view.setController( controller );

        controller.start();
    }

    @Override
    public void stop() {
        if ( controller != null ) {
            controller.stop();
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
