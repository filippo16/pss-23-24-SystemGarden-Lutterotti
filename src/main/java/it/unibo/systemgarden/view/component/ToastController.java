package it.unibo.systemgarden.view.component;

import java.io.IOException;

import it.unibo.systemgarden.view.utils.ToastType;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Controller for the Toast component.
*/
public class ToastController {
    
    private static final String FXML_PATH = "fxml/component/Toast.fxml";
    private static final int DISPLAY_TIME_MS = 3000;

    @FXML
    private StackPane toastRoot;

    @FXML
    private HBox toastBox;

    @FXML
    private Label messageLabel;

    private Stage toastStage;

    /**
     * Displays a toast notification.
     * @param owner the owner stage
     * @param message the message to display
     * @param type the type of toast
    */
    public static void show(Stage owner, String message, ToastType type) {
        try {
            FXMLLoader loader = new FXMLLoader( ClassLoader.getSystemResource(FXML_PATH) );
            loader.load();
            
            ToastController controller = loader.getController();
            controller.setup( owner, message, type );
            controller.display();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load Toast FXML.");
        }
    }

    /**
     * Sets up the toast with the given parameters.
     * @param owner the owner stage
     * @param message the message to display
     * @param type the type of toast
    */
    private void setup(Stage owner, String message, ToastType type) {

        messageLabel.setText(message);


        toastBox.getStyleClass().add(type.getStyleClass());
        

        toastStage = new Stage();
        toastStage.initOwner(owner);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        
        Scene scene = new Scene(toastRoot);
        scene.setFill(null);

        toastStage.setScene(scene);
        

        toastStage.setOnShown(e -> {
            toastStage.setX( owner.getX() + ( owner.getWidth() - toastStage.getWidth() ) / 2 );
            toastStage.setY( owner.getY() + owner.getHeight() - 100 );
        });
    }

    /**
     * Displays the toast for a predefined duration.
    */
    private void display() {
        toastStage.show();
        
        PauseTransition delay = new PauseTransition( Duration.millis( DISPLAY_TIME_MS ) );
        delay.setOnFinished( e -> toastStage.close() );
        delay.play();
    }

}
