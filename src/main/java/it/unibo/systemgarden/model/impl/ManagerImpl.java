package it.unibo.systemgarden.model.impl;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;

public class ManagerImpl implements Manager {

    private final Map<String, GreenArea> greenAreas;

    public ManagerImpl() {
        this.greenAreas = new ConcurrentHashMap<>();
    }

    @Override
    public GreenArea createGreenArea( final String name, final String city ) {
        final GreenArea area = new GreenAreaImpl( name, city );
        greenAreas.put( area.getId(), (GreenAreaImpl) area );

        return area;
    }

    @Override
    public boolean removeGreenArea( final String areaId ) {
        GreenArea area = greenAreas.remove( areaId );
    
        return area != null;
    }

    @Override
    public GreenArea getGreenArea( final String areaId ) {
        return greenAreas.get( areaId );
    }

    @Override
    public List<GreenArea> getGreenAreas() {
        return new ArrayList<>( greenAreas.values() );
    }

    @Override
    public List<GreenArea> checkAllSchedules() {
        List<GreenArea> changedAreas = greenAreas.values().stream()
            .filter( GreenArea::checkSchedules ).toList();

        return new ArrayList<>( changedAreas );
    }

    @Override
    public Sector addSectorToArea( final String areaId, final String sectorName ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            final Sector sector = new SectorImpl( sectorName );
            area.addSector( sector ); 

            return sector;
        }

        return null;
    }

    @Override
    public boolean removeSectorFromArea( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream()
                .filter(s -> s.getId().equals( sectorId )).findFirst()
                .ifPresent( area::removeSector );

            return true;
        }

        return false;
    }

    @Override
    public Sector irrigateSector( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream()
                .filter(s -> s.getId().equals( sectorId )).findFirst()
                .ifPresent( Sector::irrigate );
            
            return area.getSector( sectorId );
        }

        return null;
    }

    @Override
    public Sector stopSector( final String areaId, final String sectorId ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream()
                .filter(s -> s.getId().equals( sectorId )).findFirst()
                .ifPresent( Sector::stop );
            return area.getSector( sectorId );
        }

        return null;
    }

    @Override
    public Sector updateSectorSchedule( final String areaId, final String sectorId, 
        final java.time.LocalTime startTime, final int duration, final List<Integer> activeDays 
    ) {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            area.getSectors().stream()
                .filter(s -> s.getId().equals( sectorId )).findFirst()
                .ifPresent( s -> s.getSchedule().update( startTime, duration, activeDays ) );

            return area.getSector( sectorId );
        }

        return null;
    }

}
