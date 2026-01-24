package it.unibo.systemgarden.model.impl;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Manager;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.factory.SensorFactory;
import it.unibo.systemgarden.model.api.observer.AdvisorObservable;
import it.unibo.systemgarden.model.api.observer.AdvisorObserver;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.impl.sensor.SensorFactoryImpl;
import it.unibo.systemgarden.model.utils.SensorType;

public class ManagerImpl implements Manager {

    private final Map<String, GreenArea> greenAreas;
    SensorFactory factory = new SensorFactoryImpl();

    public ManagerImpl() {
        this.greenAreas = new ConcurrentHashMap<>();
    }

    @Override
    public GreenArea createGreenArea( final String name, final String city, final AdvisorObserver observer ) throws ActionMethodException {
        try {
            final GreenArea area = new GreenAreaImpl( name, city );
            greenAreas.put( area.getId(), (GreenAreaImpl) area );

            if (observer != null && area instanceof AdvisorObservable) {
                ((AdvisorObservable) area).addAdvisorObserver(observer);
            }

            return area;
        } catch(Exception e) {
            throw new ActionMethodException("Non è stato possibile creare l'area verde.");
        }
    }

    @Override
    public boolean removeGreenArea( final String areaId, final AdvisorObserver observer ) throws ActionMethodException {
        try {
            GreenArea area = greenAreas.remove( areaId );
            
            if (area != null && observer != null && area instanceof AdvisorObservable) {
                ((AdvisorObservable) area).removeAdvisorObserver(observer);
            }

            return area != null;
        } catch(Exception e) {
            throw new ActionMethodException("Non è stato possibile rimuovere l'area verde.");
        }
        
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
    public Sector addSectorToArea( final String areaId, final String sectorName ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            return area.addSector( areaId, sectorName );
        }

        throw new ActionMethodException("Non è stato possibile aggiungere il settore.");
    }

    @Override
    public boolean removeSectorFromArea( final String areaId, final String sectorId ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            return area.removeSector( sectorId );
        }

        throw new ActionMethodException("Non è stato possibile rimuovere il settore.");
    }

    @Override
    public Sector irrigateSector( final String areaId, final String sectorId ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {  
            return area.irrigateSector( sectorId );
        }

        throw new ActionMethodException("Non è stato possibile irrigare il settore.");
    }

    @Override
    public Sector stopSector( final String areaId, final String sectorId ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            return area.stopSector( sectorId );
        }

        throw new ActionMethodException("Non è stato possibile fermare il settore.");
    }

    @Override
    public Sector updateSectorSchedule( final String areaId, final String sectorId, 
        final LocalTime startTime, final int duration, final List<Integer> activeDays 
    ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            final Sector sector = area.updateSectorSchedule( sectorId, startTime, duration, activeDays );
            if( sector != null ) {
                return sector;
            }
        }

        throw new ActionMethodException("Non è stato possibile aggiornare il programma del settore.");
    }

    @Override
    public void refreshSensorData() {
        for ( GreenArea area : greenAreas.values() ) {
            final List<Sensor> s = area.getSensors();
            s.forEach( sensor -> sensor.refresh( area.getId(), sensor.getType() ) );
        }
    }

    @Override
    public GreenArea addSensorToArea( final String areaId, final String name, 
        final SensorType type, final SensorObserver observer 
    ) throws ActionMethodException {
        final GreenArea area = greenAreas.get( areaId );

        if ( area != null ) {
            Sensor sensor = factory.createSensor(name, type);
            area.addSensor( sensor, observer );
            return area;
        }
        throw new ActionMethodException("Non è stato possibile aggiungere il sensore.");
        
    }

    @Override
    public boolean removeSensorFromArea(String areaId, String sensorId, SensorObserver observer) throws ActionMethodException {
        GreenArea area = greenAreas.get(areaId);
        
        if (area != null) {
            return area.removeSensor(sensorId, observer);
        }
        
        throw new ActionMethodException("Non è stato possibile rimuovere il sensore.");
    }


    @Override
    public GreenArea createDemo(final AdvisorObserver observer, final SensorObserver sensorObserver)
        throws ActionMethodException 
    {


        final GreenArea demoArea = createGreenArea( "Giardino Demo", "Bologna", observer );

        addSectorToArea( demoArea.getId(), "Prato Principale" );


        addSensorToArea( demoArea.getId(), "Sensore Umidità", 
        SensorType.HUMIDITY, sensorObserver );
        addSensorToArea( demoArea.getId(), "Sensore Temperatura", 
        SensorType.TEMPERATURE, sensorObserver );


        return demoArea;
    }

}