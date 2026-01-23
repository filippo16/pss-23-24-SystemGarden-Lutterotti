package it.unibo.systemgarden.controller.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.impl.ManagerImpl;
import it.unibo.systemgarden.model.impl.sensor.AbstractSensor;
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
        
        // Calculate initial delay to align with the start of the next minute
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
        final GreenArea area = model.createGreenArea( name, city );

        if( area != null ) {
            view.addAreaCard( area );
            view.showToast( "Aggiunta nuova area verde " + name, ToastType.SUCCESS );
        }
    }

    @Override
    public void removeGreenArea(final String areaId) {
        final boolean removed = model.removeGreenArea( areaId );

        if ( removed ) {
            view.removeAreaCard( areaId );
            view.showToast( "Rimossa area verde con successo", ToastType.SUCCESS );
        }
    }

    @Override
    public GreenArea getGreenArea( final String areaId ) {
        return model.getGreenArea( areaId );
    }

    @Override
    public void addSectorToArea( final String areaId, final String sectorName ) {

        final Sector sector = model.addSectorToArea( areaId, sectorName );

        if ( sector != null ) {
            view.addSectorCard(areaId, sector);

        }
    }

    @Override
    public void removeSectorFromArea( final String areaId, final String sectorId ) {
        final boolean removed = model.removeSectorFromArea( areaId, sectorId );

        if ( removed ) {
            view.removeSectorCard(areaId, sectorId);
        }
    }

    @Override
    public void irrigateSector( final String areaId, final String sectorId ) {
        final Sector sector = model.irrigateSector( areaId, sectorId );

        if ( sector != null ) {
            view.refreshSectorCard( areaId, sector );
        }
    }

    @Override
    public void stopSector( final String areaId, final String sectorId ) {
        final Sector sector = model.stopSector( areaId, sectorId );

        if ( sector != null ) {
            view.refreshSectorCard( areaId, sector );
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
        final Sector sector = model.updateSectorSchedule(areaId, sectorId, 
            startTime, duration, activeDays
        );

        if ( sector != null ) {
            view.refreshSectorCard( areaId, sector );
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
    public void addSensorToArea( String areaId, String name, SensorType type ) {
       final GreenArea area = model.addSensorToArea( areaId, name, type );

       if( area != null ) {
            model.getGreenArea( area.getId() ).getSensors().stream()
                .forEach( s -> ( (AbstractSensor) s ).addObserver( (SensorObserver) view ) );
            
            view.refreshAreaCard(area);
       }
    }

    @Override
    public void removeSensorFromArea( String areaId, String sensorId ) {
        final boolean removed = model.removeSensorFromArea( areaId, sensorId );

        if ( removed ) {
            final GreenArea area = model.getGreenArea( areaId );
            view.refreshAreaCard( area );
        }
    }
 
}