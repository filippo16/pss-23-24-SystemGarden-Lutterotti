package it.unibo.systemgarden.controller.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.impl.ManagerImpl;
import it.unibo.systemgarden.view.api.View;
import javafx.application.Platform;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the Controller interface.
 */
public class ControllerImpl implements Controller {

    private final View view;
    //private final Map<String, GreenArea> greenAreas;
    private final Manager model;
    private ScheduledExecutorService scheduler;



    /**
     * Creates a new controller.
     * 
     * @param view the view to update
     */
    public ControllerImpl( final View view ) {
        this.view = view;
        this.model = new ManagerImpl();
    }

    @Override
    public void start() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::checkAllSchedules, 0, 30, TimeUnit.SECONDS);
        view.show();
    }

    @Override
    public void stop() {
        System.out.println( "[Controller] System stopped" );
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    @Override
    public void createGreenArea( final String name, final String city ) {
        final GreenArea area = model.createGreenArea( name, city );

        if( area != null ) {
            view.addAreaCard( area );
        }
        
    }

    @Override
    public void removeGreenArea(final String areaId) {
        final GreenArea area = model.removeGreenArea( areaId );

        if ( area != null ) {
            view.removeAreaCard( area );
        }
    }

    @Override
    public GreenArea getGreenArea( final String areaId ) {
        return model.getGreenArea( areaId );
    }

    @Override
    public void addSectorToArea( final String areaId, final String sectorName ) {

        final GreenArea area = model.addSectorToArea( areaId, sectorName );

        if ( area != null ) {
            view.refreshAreaCard( area );
        }
    }

    @Override
    public void removeSectorFromArea( final String areaId, final String sectorId ) {
        final GreenArea area = model.removeSectorFromArea( areaId, sectorId );

        if ( area != null ) {
            view.refreshAreaCard( area );
        }
    }

    @Override
    public void irrigateSector( final String areaId, final String sectorId ) {
        final GreenArea area = model.irrigateSector( areaId, sectorId );

        if ( area != null ) {
            view.refreshAreaCard( area );
        }
    }

    @Override
    public void stopSector( final String areaId, final String sectorId ) {
        final GreenArea area = model.stopSector( areaId, sectorId );

        if ( area != null ) {
            view.refreshAreaCard( area );
        }
    }

    private void checkAllSchedules() {
        List<GreenArea> changedAreas = model.checkAllSchedules();

        if (!changedAreas.isEmpty()) {
            Platform.runLater(() -> 
                changedAreas.forEach(view::refreshAreaCard)
            );
        }
    }

    @Override
    public void updateSectorSchedule( final String areaId, final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    ) {
        final GreenArea area = model.updateSectorSchedule(areaId, sectorId, 
            startTime, duration, activeDays
        );

        if ( area != null ) {

            view.refreshAreaCard( area );
        }
    }
}
