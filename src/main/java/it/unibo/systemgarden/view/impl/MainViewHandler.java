package it.unibo.systemgarden.view.impl;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import it.unibo.systemgarden.view.utils.DialogHelper;


/**
 * Handler for the main view FXML. 
 * Manages the methods called in the view for buttons and other components. 
 */
public class MainViewHandler {

    private String css;

    public void setCssStylesheet(final String css) {
        this.css = css;
    }

    @FXML
    private void onAddAreaClicked() {
        final String[] result = DialogHelper.showAddAreaDialog(css);
        
        if (result != null) {
            System.out.println("New Area - Name: " + result[0] + ", City: " + result[1]);
        } else {
            System.out.println("Add Area dialog was cancelled.");
        }

    }

   
}
