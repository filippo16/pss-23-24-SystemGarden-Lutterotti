package it.unibo.systemgarden.view.impl;

import javafx.fxml.FXML;
import it.unibo.systemgarden.view.utils.DialogHelper;


/**
 * Handler for the main view FXML. 
 * Manages the methods called in the view for buttons and other components. 
 */
public class MainViewHandler {

    @FXML
    private void onAddAreaClicked() {
        final String[] result = DialogHelper.showAddAreaDialog();
        
        if (result != null) {
            System.out.println("New Area - Name: " + result[0] + ", City: " + result[1]);
        } else {
            System.out.println("Add Area dialog was cancelled.");
        }

    }

   
}
