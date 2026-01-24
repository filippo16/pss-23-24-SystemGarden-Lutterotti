package it.unibo.systemgarden.controller.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.observer.AdvisorObserver;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.impl.ManagerImpl;
import it.unibo.systemgarden.model.utils.SensorType;
import it.unibo.systemgarden.view.api.View;
import it.unibo.systemgarden.view.utils.ToastType;
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
        
        long now = System.currentTimeMillis();
        long delayToNextMinute = 60000 - ( now % 60000 );
        
        scheduler.scheduleAtFixedRate(() -> {

            checkAllSchedules();
            updateClocks();
            model.refreshSensorData(); 

        }, delayToNextMinute, 60000, TimeUnit.MILLISECONDS);
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
        try {

            final GreenArea area = model.createGreenArea( name, city, (AdvisorObserver) view );

            if( area != null ) {
                view.addAreaCard( area );
                view.showToast( "Aggiunta nuova area verde " + name, ToastType.SUCCESS );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void removeGreenArea(final String areaId) {
        try {

            final boolean removed = model.removeGreenArea( areaId, (AdvisorObserver) view );

            if ( removed ) {
                view.removeAreaCard( areaId );
                view.showToast( "Rimossa area verde con successo", ToastType.SUCCESS );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void addSectorToArea( final String areaId, final String sectorName ) {
        try {

            final Sector sector = model.addSectorToArea( areaId, sectorName );

            if ( sector != null ) {
                view.addSectorCard( areaId, sector );
                view.showToast( "Aggiunto nuovo settore " + sectorName, ToastType.SUCCESS );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void removeSectorFromArea( final String areaId, final String sectorId ) {
        try {

            final boolean removed = model.removeSectorFromArea( areaId, sectorId );

            if ( removed ) {
                view.removeSectorCard(areaId, sectorId);
                view.showToast( "Rimosso settore con successo", ToastType.SUCCESS );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void irrigateSector( final String areaId, final String sectorId ) {
        try {

            final Sector sector = model.irrigateSector( areaId, sectorId );

            if ( sector != null ) {
                view.refreshSectorCard( areaId, sector );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void stopSector( final String areaId, final String sectorId ) {
        try {

            final Sector sector = model.stopSector( areaId, sectorId );

            if ( sector != null ) {
                view.refreshSectorCard( areaId, sector );
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    private void checkAllSchedules() {
        List<GreenArea> changedAreas = model.checkAllSchedules();

        if (!changedAreas.isEmpty()) {
            Platform.runLater(() -> // Thread safe
                changedAreas.forEach(view::refreshAreaCard)
            );
        }
    }

    @Override
    public void updateSectorSchedule( final String areaId, final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    ) {
        try {
            final Sector sector = model.updateSectorSchedule(areaId, sectorId, 
                startTime, duration, activeDays
            );

            if ( sector != null ) {
                view.refreshSectorCard( areaId, sector );
            }
        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
        
    }

    private void updateClocks() {
        Platform.runLater(() -> {
            model.getGreenAreas().forEach(area -> {
                view.updateAreaClock( area.getId(), area.getLocation().getLocalTime() );
            });
        });
    }

    @Override
    public void addSensorToArea( final String areaId, final String name, final SensorType type ) {
        try {

            final GreenArea area = model.addSensorToArea( areaId, name, type, (SensorObserver) view );

            if( area != null ) {
                    view.refreshAreaCard(area);
            }

        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }

    @Override
    public void removeSensorFromArea( final String areaId, final String sensorId ) {
        try {

            final boolean removed = model.removeSensorFromArea( areaId, sensorId, (SensorObserver) view );

            if ( removed ) {
                final GreenArea area = model.getGreenArea( areaId );
                view.refreshAreaCard( area );
            }
            
        } catch(ActionMethodException e) {
            view.showToast( e.getMessage(), ToastType.ERROR );
        }
    }
 
}