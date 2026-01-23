package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Location;
import it.unibo.systemgarden.model.api.Schedule;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.impl.advisor.SmartAdvisorImpl;
import it.unibo.systemgarden.model.utils.IrrigationAdvice;
import it.unibo.systemgarden.model.utils.SensorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of GreenArea interface.
 */
public class GreenAreaImpl implements GreenArea, SensorObserver {

    private final String id;
    private final String name;
    private final Location location;
    private final List<Sector> sectors;
    private final List<Sensor> sensors;
    private final SmartAdvisorImpl advisor;

    /**
     * Creates a new green area.
     * 
     * @param name the area name
     * @param city the city where the area is located
     */
    public GreenAreaImpl( final String name, final String city ) {
        this.id = "AREA-" + new Random().nextInt( 100000 );
        this.name = name;
        this.location = new LocationImpl(city);
        this.sectors = new ArrayList<>();
        this.sensors = new ArrayList<>();
        this.advisor = new SmartAdvisorImpl();
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Sector> getSectors() {
        return new ArrayList<>( this.sectors );
    }

    @Override
    public Sector getSector( final String sectorId ) {
        return this.sectors.stream()
            .filter( s -> s.getId().equals( sectorId ) )
            .findFirst()
            .orElse( null );
    }

    @Override
    public void addSector( final Sector sector ) {
        if ( !sectors.contains( sector ) ) {
            sectors.add( sector );
        }
    }

    @Override
    public void removeSector( final Sector sector ) {
        sector.stop();
        sectors.remove( sector );
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public boolean checkSchedules() {
        boolean changed = false;

        for (Sector sector : sectors) {
            Schedule schedule = sector.getSchedule();
            
            if (schedule != null) {

                if (schedule.shouldStartNow(location.getTimezone()) && 
                !sector.isIrrigating()
                ) {
                    sector.irrigate();
                    changed = true;
                }

                if (schedule.shouldStopNow(location.getTimezone()) && 
                sector.isIrrigating()
                ) {
                    sector.stop();
                    changed = true;
                }

            }
        }
        return changed;
    }


    public List<Sensor> getSensors() {
        return new ArrayList<>( this.sensors );
    }

    @Override
    public void addSensor( final Sensor sensor ) {
        if ( !sensors.contains( sensor ) ) {
            sensors.add( sensor );
        }
    }

    @Override
    public boolean removeSensor( final String sensorId ) {
        return sensors.removeIf(sensor -> sensor.getId().equals(sensorId));
    }


    @Override
    public void onSensorUpdate( String areaId, String sensorId, double newValue, SensorType type ) {
        Set<SensorType> typeSet = sensors.stream()
            .map(Sensor::getType)
            .collect(Collectors.toSet());

        IrrigationAdvice advice = advisor.getAdvice( typeSet, newValue, type );

        // notify( areaId, advice.getTitle())
    }

}
