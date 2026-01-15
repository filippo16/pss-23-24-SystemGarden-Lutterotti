package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.view.dialog.AddAreaDialogController;
import it.unibo.systemgarden.view.dialog.AddSectorDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Helper class to show FXML dialogs.
 */
public final class DialogHelper {

    private static final String FXML_PATH_AREA_DIALOG = "fxml/dialog/AddAreaDialog.fxml";
    private static final String FXML_PATH_SECTOR_DIALOG = "fxml/dialog/AddSectorDialog.fxml";

    /**
     * Shows the Add Area dialog.
     * 
     * @return [name, city] or null if cancelled
    */
    public static String[] showAddAreaDialog(String css) {
        try {
            final FXMLLoader loader = new FXMLLoader( ClassLoader.getSystemResource( FXML_PATH_AREA_DIALOG ) );
            final Parent root = loader.load();
            final String title = "+ Nuova Area Verde";
            final Stage stage = createDialogStage(title, root, css);

            
            final AddAreaDialogController controller = loader.getController();
            controller.setStage( stage );

            stage.showAndWait();

            return controller.getResult();
        } catch (Exception e) {
            throw new RuntimeException("Error showing Add Area Dialog: " + e.getMessage(), e);
        }
    }

    public static String showAddSectorDialog(String css) {
        try  {
            final FXMLLoader loader = new FXMLLoader( ClassLoader.getSystemResource( FXML_PATH_SECTOR_DIALOG ) );
            final Parent root = loader.load();

            final Stage dialog = createDialogStage( "Nuovo Settore", root, css );
            final AddSectorDialogController controller = loader.getController();
            controller.setStage( dialog );

            dialog.showAndWait();

            return controller.getResult();
        } catch (Exception e) {

            throw new RuntimeException( "Error showing Add Sector Dialog: " + e.getMessage(), e );

        }

    }
    
    private static Stage createDialogStage( final String title, final Parent root, final String css ) {
        final Stage stage = new Stage();
        stage.setTitle( title );
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.initStyle( StageStyle.UTILITY );
        stage.setResizable(false);

        final Scene dialogScene = new Scene(root);

        if (css != null) {
            dialogScene.getStylesheets().setAll(css);
        }

        stage.setScene(dialogScene);
        return stage;
    }
}
