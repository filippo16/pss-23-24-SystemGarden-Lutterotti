package it.unibo.systemgarden.view.component;

import it.unibo.systemgarden.view.utils.ToastType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ToastController {
    
    private static final String FXML_PATH = "fxml/component/Toast.fxml";

    @FXML
    private StackPane toastRoot;

    @FXML
    private HBox toastBox;

    @FXML
    private Label messageLabel;

    private Stage toastStage;

    private void setup(Stage owner, String message, ToastType type) {

        messageLabel.setText(message);


        toastBox.getStyleClass().add(type.getStyleClass());
        

        toastStage = new Stage();
        toastStage.initOwner(owner);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        
        Scene scene = new Scene(toastRoot);

        toastStage.setScene(scene);
        

        toastStage.setOnShown(e -> {
            toastStage.setX(owner.getX() + ( owner.getWidth() - toastStage.getWidth() ) / 2);
            toastStage.setY(owner.getY() + owner.getHeight() - 100);
        });
    }

}
