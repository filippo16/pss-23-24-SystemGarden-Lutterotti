package it.unibo.systemgarden.model.api.observer;

public interface AdvisorObservable {
    
    void addObserver( AdvisorObserver observer );

    void removeObserver( AdvisorObserver observer );

    void notifyObservers( String areaId, String adviceTitle );
}
