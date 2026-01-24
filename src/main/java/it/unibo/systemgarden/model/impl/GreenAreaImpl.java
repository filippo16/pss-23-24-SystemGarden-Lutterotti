package it.unibo.systemgarden.model.impl;

import it.unibo.systemgarden.model.api.GreenArea;
import it.unibo.systemgarden.model.api.Location;
import it.unibo.systemgarden.model.api.Schedule;
import it.unibo.systemgarden.model.api.Sector;
import it.unibo.systemgarden.model.api.Sensor;
import it.unibo.systemgarden.model.api.exception.ActionMethodException;
import it.unibo.systemgarden.model.api.observer.AdvisorObservable;
import it.unibo.systemgarden.model.api.observer.AdvisorObserver;
import it.unibo.systemgarden.model.api.observer.SensorObserver;
import it.unibo.systemgarden.model.impl.advisor.SmartAdvisorImpl;
import it.unibo.systemgarden.model.impl.sensor.AbstractSensor;
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
public class GreenAreaImpl implements GreenArea, SensorObserver, AdvisorObservable {

    private static final int MAX_SENSORS = 2;


    private final String id;
    private final String name;
    private final Location location;
    private final List<Sector> sectors;
    private final List<Sensor> sensors;
    private final SmartAdvisorImpl advisor;

    private final List<AdvisorObserver> advisorObservers;

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
        this.advisorObservers = new ArrayList<>();
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
    public Sector addSector( final String areaId, final String sectorName ) {
        final Sector sector = new SectorImpl( sectorName );
        if ( !sectors.contains( sector ) ) {
            sectors.add( sector );
            return sector;
        }
        return null;
    }

    @Override
    public boolean removeSector( final String sectorId ) {
        Sector sector = getSector(sectorId);
        if (sector != null) {
            sector.stop();
            sectors.remove( sector );
            return true;
        }
        return false;
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


    @Override
    public Sector irrigateSector( final String sectorId ) {
        Sector sector = getSector( sectorId );
        if (sector != null) {
            sector.irrigate();
        }
        return sector;
    }

    @Override
    public Sector stopSector( final String sectorId ) {
        Sector sector = getSector( sectorId );
        if (sector != null) {
            sector.stop();
        }
        return sector;
    }

    public Sector updateSectorSchedule( final String sectorId, final java.time.LocalTime startTime,
        final int duration, final List<Integer> activeDays 
    )  {
        final Sector sector = getSector( sectorId );

        if ( sector != null ) {
            sector.updateSchedule(startTime, duration, activeDays);
            return sector;
        }
        return null;
    }


    public List<Sensor> getSensors() {
        return new ArrayList<>( this.sensors );
    }

    @Override
    public void addSensor( final Sensor sensor, final SensorObserver observer  ) throws ActionMethodException {
        if (sensors.size() >= MAX_SENSORS) {
            throw new ActionMethodException("Numero massimo di sensori raggiunto (2).");
        }

        if ( !sensors.contains( sensor ) ) {
            if (sensor instanceof AbstractSensor) {
                ((AbstractSensor) sensor).addObserver(this);
                ((AbstractSensor) sensor).addObserver( (SensorObserver) observer );
            }
            sensors.add( sensor );
        }
    }

    @Override
    public boolean removeSensor( final String sensorId, final SensorObserver observer  ) {
        final Sensor sensor = sensors.stream()
            .filter( s -> s.getId().equals( sensorId ) )
            .findFirst()
            .orElse( null );

        if (sensor == null) {
            return false;
        }
        if (sensor instanceof AbstractSensor) {
            final AbstractSensor abs = (AbstractSensor) sensor;
            abs.removeObserver(this);
            if (observer != null) {
                abs.removeObserver(observer);
            }
            
        }
        return sensors.remove(sensor);
    }


    @Override
    public void onSensorUpdate( String areaId, String sensorId, double newValue, SensorType type ) {
        
        if (!this.id.equals(areaId)) {
            return; 
        }

        Set<SensorType> typeSet = sensors.stream()
            .map(Sensor::getType)
            .collect(Collectors.toSet());

        IrrigationAdvice advice = advisor.getAdvice( typeSet, newValue, type );

        notifyAdvisorObservers( areaId, advice.getTitle());
    }

    @Override
    public void addAdvisorObserver( AdvisorObserver observer ) {
        if (!advisorObservers.contains( observer )) {
            advisorObservers.add( observer );
        }
    }

    @Override
    public void removeAdvisorObserver( AdvisorObserver observer ) {
        advisorObservers.remove( observer );
    }

    @Override
    public void notifyAdvisorObservers( String areaId, String adviceTitle ) {
        advisorObservers.forEach( obs -> obs.onAdviceReceived( areaId, adviceTitle ) );
    }
}