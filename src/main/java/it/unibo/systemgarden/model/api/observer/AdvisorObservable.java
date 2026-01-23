package it.unibo.systemgarden.model.api.observer;

public interface AdvisorObservable {

    void addAdvisorObserver( AdvisorObserver observer );

    void removeAdvisorObserver( AdvisorObserver observer );

    void notifyAdvisorObservers( String areaId, String adviceTitle );
}
