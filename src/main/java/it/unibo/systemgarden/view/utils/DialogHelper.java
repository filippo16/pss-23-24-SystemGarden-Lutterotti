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
    public static String[] showAddAreaDialog(String css) {
        try {
            final FXMLLoader loader = new FXMLLoader( DialogHelper.class.getResource("/fxml/dialog/AddAreaDialog.fxml") );
            final Parent root = loader.load();
            final String title = "+ Nuova Area Verde";
            final Scene dialogScene = new Scene( root );

            if (css != null) {
                dialogScene.getStylesheets().setAll(css);
            }

            final Stage stage = new Stage();
            stage.setTitle( title );
            stage.initStyle( StageStyle.UTILITY ); // Utilty style for simple dialogs
            stage.setScene( dialogScene );

            
            final AddAreaDialogController controller = loader.getController();
            controller.setStage( stage );

            stage.showAndWait();

            return controller.getResult();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
