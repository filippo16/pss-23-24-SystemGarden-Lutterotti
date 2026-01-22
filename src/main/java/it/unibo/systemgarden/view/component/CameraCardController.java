package it.unibo.systemgarden.view.component;

import javafx.fxml.FXML;

public class CameraCardController {

    @FXML
    private String nameLabel;

    public void initialize( final String name ) {
        this.nameLabel = name;
    }

    @FXML
    private void showCamera() {
        // Open the dialog to show the camera
    }
    
}
