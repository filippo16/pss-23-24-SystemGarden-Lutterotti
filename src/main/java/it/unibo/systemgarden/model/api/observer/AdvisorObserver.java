package it.unibo.systemgarden.model.api.observer;

public interface AdvisorObserver {

    void onAdviceReceived( String areaId, String adviceTitle );
}
