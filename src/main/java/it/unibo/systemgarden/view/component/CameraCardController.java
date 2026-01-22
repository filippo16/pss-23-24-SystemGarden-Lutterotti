package it.unibo.systemgarden.view.component;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CameraCardController {

    private static final String FXML_PATH_CAMERA_DIALOG = "fxml/dialog/ShowCameraDialog.fxml";

    @FXML
    private Label nameLabel;

    public void initialize( final String name ) {
        this.nameLabel.setText( name );
    }

    @FXML
    private void showCamera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH_CAMERA_DIALOG));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Camera");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("Error loading camera dialog.");
        }
    }
    
}
