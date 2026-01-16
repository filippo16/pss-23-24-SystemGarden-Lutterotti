package it.unibo.systemgarden.controller.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.impl.GreenAreaImpl;
import it.unibo.systemgarden.model.impl.SectorImpl;
import it.unibo.systemgarden.view.api.View;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the Controller interface.
 */
public class ControllerImpl implements Controller {

    private final View view;
    private final Map<String, GreenArea> greenAreas;
    private ScheduledExecutorService scheduler;



    /**
     * Creates a new controller.
     * 
     * @param view the view to update
     */
    public ControllerImpl( final View view ) {
        this.view = view;
        this.greenAreas = new ConcurrentHashMap<>();
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
        final GreenArea area = new GreenAreaImpl( name, city );

        if( area != null ) {
            greenAreas.put( area.getId(), area );
            view.addAreaCard( area );
            System.out.println( "[Controller] Created area: " + name );
        }
        
    }

    @Override
    public void removeGreenArea(final String areaId) {
        final GreenArea area = greenAreas.remove( areaId );

        if ( area != null ) {
            view.removeAreaCard( area );
            System.out.println( "[Controller] Removed area: " + area.getName() );
        }
    }

    @Override
    public List<GreenArea> getGreenAreas() {
        return new ArrayList<>( greenAreas.values() );
    }

    @Override
    public GreenArea getGreenArea( final String areaId ) {
        return greenAreas.get( areaId );
    }

    @Override
    public void addSectorToArea( final String areaId, final String sectorName ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            final Sector sector = new SectorImpl( sectorName );
            area.addSector( sector ); 
            view.refreshAreaCard( area );
            System.out.println( "[Controller] Added sector: " + sectorName );
        }
    }

    @Override
    public void removeSectorFromArea( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream().filter(s -> s.getId().equals( sectorId )).findFirst().ifPresent( area::removeSector );
            view.refreshAreaCard( area );
        }
    }

    @Override
    public void irrigateSector( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream().filter(s -> s.getId().equals( sectorId )).findFirst().ifPresent( Sector::irrigate );
            view.refreshAreaCard( area );
        }
    }

    @Override
    public void stopSector( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream().filter(s -> s.getId().equals( sectorId )).findFirst().ifPresent( Sector::stop );
            view.refreshAreaCard( area );
        }
    }

    private void checkAllSchedules() {
        greenAreas.values().forEach( GreenArea::checkSchedules );
    }

    @Override
    public void updateSectorSchedule( final String areaId, final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    ) {

        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream().filter(s -> s.getId().equals( sectorId )).findFirst()
                .ifPresent(sector -> {
                    sector.getSchedule().update( startTime, duration, activeDays );
                    view.refreshAreaCard( area );
                });
        }
    }
}
