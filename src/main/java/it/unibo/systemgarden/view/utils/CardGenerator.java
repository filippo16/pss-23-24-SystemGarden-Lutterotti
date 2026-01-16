package it.unibo.systemgarden.view.utils;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Schedule;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.view.dialog.AddSectorDialogController;
import it.unibo.systemgarden.view.dialog.EditScheduleDialogController;
import it.unibo.systemgarden.view.dialog.ScheduleData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CardGenerator {

    private static final String FXML_PATH_SECTOR_DIALOG = "fxml/dialog/AddSectorDialog.fxml";
    private static final String FXML_PATH_SCHEDULE_DIALOG = "fxml/dialog/EditScheduleDialog.fxml";

    private String css;

    public CardGenerator(final String css) {
        this.css = css;
    }


    /**
     * Creates a card that representing a GreenArea.
    */
    public VBox createAreaCard(Controller controller, final GreenArea area) {
        final VBox card = new VBox(10);
        card.setId(area.getId());
        card.getStyleClass().add("area-card");
        card.setPadding(new Insets(15));

        // Structure
        final HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        final Label nameLabel = new Label(area.getName());
        nameLabel.getStyleClass().add("area-name");

        final Label cityLabel = new Label("(" + area.getLocation().getCity() + ")");
        cityLabel.setStyle("-fx-text-fill: #666666;");

        // Add clock
        final DateTimeFormatter clockFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        final Label clockLabel = new Label(area.getLocation().getLocalTime().format(clockFormatter));
        clockLabel.getStyleClass().add("clock-label");

        final Timeline clockTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> 
                clockLabel.setText(area.getLocation().getLocalTime().format(clockFormatter))
            )
        );
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();

        // Stop the timer when the card is removed from the scene
        card.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                clockTimeline.stop();
            }
        });

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        final Button deleteBtn = new Button("X");
        deleteBtn.getStyleClass().add("danger-button");
        deleteBtn.setOnAction(e -> {
            controller.removeGreenArea( area.getId() );
        });

        header.getChildren().addAll(nameLabel, cityLabel, spacer, clockLabel, deleteBtn);

        final VBox sectorsSection = createSectorsSection(controller, area);

        card.getChildren().addAll(header, sectorsSection );
        return card;
    }

    /**
     * Creates the sectors section for a GreenArea card.
    */
    private VBox createSectorsSection(Controller controller, final GreenArea area) {
        final VBox box = new VBox(5);

        final HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        final Label title = new Label("Settori");
        title.getStyleClass().add("section-label");

        final Button addBtn = new Button("Aggiungi Settore");
        addBtn.getStyleClass().add("btn-primary");
        addBtn.setOnAction(e -> {
            final String result = DialogHelper.<String, AddSectorDialogController>showDialog(FXML_PATH_SECTOR_DIALOG, "Nuovo Settore", css);
            if (result != null) {
                controller.addSectorToArea(area.getId(), result);
            } 
        });

        titleRow.getChildren().addAll(title, addBtn);
        box.getChildren().add(titleRow);

        for (final Sector sector : area.getSectors()) {
            final VBox sectorBox = new VBox(3);
            sectorBox.getStyleClass().add("sector-item");
            
            final HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            final String status = sector.isIrrigating() ? "[ON]" : "[--]";
            final Label sectorLabel = new Label(status + " " + sector.getName());
            sectorLabel.setPrefWidth(150);

            final Button startBtn = new Button("Avvia");
            startBtn.getStyleClass().add("btn-secondary");
            startBtn.setOnAction(e -> {
                controller.irrigateSector(area.getId(), sector.getId());
            });

            final Button stopBtn = new Button("Ferma");
            stopBtn.getStyleClass().add("btn-secondary");
            stopBtn.setOnAction(e -> {
                controller.stopSector(area.getId(), sector.getId());
            });

            final Button scheduleBtn = new Button("Orari");
            scheduleBtn.getStyleClass().add("btn-secondary");
            scheduleBtn.setOnAction(e -> {
                final ScheduleData result = DialogHelper.<ScheduleData, EditScheduleDialogController>showDialog( 
                    FXML_PATH_SCHEDULE_DIALOG, "Modifica Programmazione", css
                );

                if (result != null) {
                    controller.updateSectorSchedule(area.getId(), sector.getId(), result.startTime(), result.duration(), result.activeDays());
                }
            });

            final Button deleteBtn = new Button("X");
            deleteBtn.getStyleClass().add("danger-button");
            deleteBtn.setOnAction(e -> {
                controller.removeSectorFromArea(area.getId(), sector.getId());
            });

            row.getChildren().addAll(sectorLabel, startBtn, stopBtn, scheduleBtn, deleteBtn);
            
            // Schedule info label
            final Label scheduleInfoLabel = new Label(formatScheduleInfo(sector.getSchedule()));
            scheduleInfoLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
            
            sectorBox.getChildren().addAll(row, scheduleInfoLabel);
            box.getChildren().add(sectorBox);
        }

        return box;
    }

    /**
     * Formats the schedule information for display.
     */
    private String formatScheduleInfo(final Schedule schedule) {
        if (schedule == null) {
            return "Nessuna programmazione";
        }
        
        final String time = schedule.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        final String duration = schedule.getDuration() + " min";
        final String days = formatDays(schedule.getActiveDays());
        
        return "⏰ " + time + " | " + duration + " | " + days;
    }

    /**
     * Formats the list of active days.
     */
    private String formatDays(final List<Integer> days) {
        if (days == null || days.isEmpty()) {
            return "Nessun giorno";
        }
        
        return days.stream()
            .sorted()
            .map(d -> switch (d) {
                case 1 -> "Lun";
                case 2 -> "Mar";
                case 3 -> "Mer";
                case 4 -> "Gio";
                case 5 -> "Ven";
                case 6 -> "Sab";
                case 7 -> "Dom";
                default -> "";
            })
            .collect(Collectors.joining(", "));
    }
}
