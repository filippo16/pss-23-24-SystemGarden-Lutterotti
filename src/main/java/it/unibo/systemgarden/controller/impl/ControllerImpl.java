package it.unibo.systemgarden.controller.impl;

import it.unibo.systemgarden.controller.api.Controller;
import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.impl.GreenAreaImpl;
import it.unibo.systemgarden.model.impl.SectorImpl;
import it.unibo.systemgarden.view.api.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the Controller interface.
 */
public class ControllerImpl implements Controller {

    private final View view;
    private final Map<String, GreenArea> greenAreas;

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
        System.out.println( "[Controller] System started" );

        view.show();
    }

    @Override
    public void stop() {
        // Stop all irrigations
        greenAreas.values().forEach( GreenArea::stopAll );
        System.out.println( "[Controller] System stopped" );
    }

    @Override
    public GreenArea createGreenArea( final String name, final String city ) {
        final GreenArea area = new GreenAreaImpl( name, city );
        greenAreas.put( area.getId(), area );
        updateView();
        System.out.println( "[Controller] Created area: " + name );
        return area;
    }

    @Override
    public void removeGreenArea(final String areaId) {
        final GreenArea area = greenAreas.remove( areaId );
        if ( area != null ) {
            area.stopAll();
            updateView();
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
            updateView();
            System.out.println( "[Controller] Added sector: " + sectorName );
        }
    }

    @Override
    public void removeSectorFromArea( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );
        if ( area != null ) {
            area.getSectors().stream()
                    .filter(s -> s.getId().equals( sectorId ))
                    .findFirst()
                    .ifPresent( area::removeSector );
            updateView();
        }
    }

    /**
     * Updates the view with current data.
     */
    private void updateView() {
        if ( view != null ) {
            view.updateGreenAreas( getGreenAreas() );
        }
    }
}
