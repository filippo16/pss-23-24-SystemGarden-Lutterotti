package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.view.dialog.AddAreaDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Helper class to show FXML dialogs.
 */
public final class DialogHelper {

    private DialogHelper() {
    }

    /**
     * Shows the Add Area dialog.
     * 
     * @return [name, city] or null if cancelled
     */
    public static String[] showAddAreaDialog() {
        try {
            final FXMLLoader loader = new FXMLLoader( DialogHelper.class.getResource("/fxml-systemgarden/AddAreaDialog.fxml") );
            final Parent root = loader.load();
            final String title = "+ Nuova Area Verde";

            final Stage stage = new Stage();
            stage.setTitle( title );
            stage.initStyle( StageStyle.UTILITY ); // Utilty style for simple dialogs
            stage.setScene( new Scene( root ) );

            stage.showAndWait();

            final AddAreaDialogController controller = loader.getController();
            controller.setStage( stage );
            
            return controller.getResult();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
